package com.boneis.board.service;

import com.boneis.board.domain.board.*;
import com.boneis.board.domain.ResultDomain;
import com.boneis.board.domain.common.ConstantValue;
import com.boneis.board.mapper.AttachMapper;
import com.boneis.board.mapper.BoardMapper;
import com.boneis.board.util.BoardUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.*;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.stream.Collectors;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService {

  @Autowired
  BoardMapper boardMapper; // Mapper 주입

  @Autowired
  AttachMapper attachMapper;

  @Override
  public BoardResponse getAllList() throws Exception {
    BoardResponse boardResponse = new BoardResponse();
    try {
      boardResponse.setBoardList(boardMapper.getAllList());
      boardResponse.setResult(ConstantValue.OK_MESSAGE);
      boardResponse.setMessage(ConstantValue.OK_MESSAGE);
    } catch (Exception e) {
      boardResponse.setResult(ConstantValue.ERR_RESULT);
      boardResponse.setMessage(ConstantValue.ERR_MESSAGE);
      log.error("[BoardServiceImpl - getAllList]" + e.getMessage());
    }
    return boardResponse;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  //try catch로 묶어놓고 끝내면 Rollback이 안됨.
  public ResultDomain insertBoard(Board board) throws Exception {
    ResultDomain result = new ResultDomain();
    try {
      // password Encryption
      board.setPassword(BoardUtils.encryption(board.getPassword()));
      boardMapper.insertBoard(board);

      boardMapper.updateReGroup(board);

      result.setResult(ConstantValue.OK_MESSAGE);
      result.setMessage(ConstantValue.OK_MESSAGE);

      // 첨부 파일이 존재할 경우 업로드 메소드 실행
//      if (!StringUtils.equals(board.getInsertParam().getFileName(), "")) {
//        log.info("..");
//        // 업로드 메소드 실행 후 결과값을 DB에 insert하기 위해 AttachBoard를 받는다.
//        AttachBoard attachBoard = BoardUtils.uploadImage(board.getInsertParam().getAttach(), board.getInsertParam().getFileName());
//        // attachBoard에 참조키값 (board_no) 저장
//        attachBoard.setBoardNo(board.getBoardNo());
//        String[] attachArr = attachBoard.getAttachPath().split(ConstantValue.SLASH);
//        attachBoard.setUrlPath(ConstantValue.PATH + ConstantValue.SLASH + attachArr[4] + ConstantValue.SLASH + attachArr[5] + ConstantValue.SLASH + attachArr[6] + ConstantValue.SLASH + attachBoard.getFileName());
//        result = insertAttach(attachBoard);
//      }

    } catch (Exception e) {
      result.setResult(ConstantValue.ERR_RESULT);
      result.setMessage(ConstantValue.ERR_MESSAGE);
      log.error("[BoardServiceImpl - InsertAttach]" + e.getMessage());
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // rollback
    }

    return result;
  }

  @Override
  public ResultDomain insertAttach(AttachBoard attachBoard) throws Exception {
    // DB insert Attach
    ResultDomain result = new ResultDomain();
    try {
      attachMapper.insertAttach(attachBoard);
      result.setMessage(ConstantValue.OK_MESSAGE);
      result.setResult(ConstantValue.OK_MESSAGE);
    } catch (Exception e) {
      result.setResult(ConstantValue.ERR_RESULT);
      result.setMessage(ConstantValue.ERR_MESSAGE);
      log.error("[BoardServiceImpl - insertAttach]" + e.getMessage());
    }
    return result;
  }

  @Override
  public BoardResponse getBoard(int boardNo) throws Exception {
    BoardResponse boardResponse = new BoardResponse();
    try {
      boardResponse.setDetailBoard(boardMapper.getBoard(boardNo));
      boardResponse.setResult(ConstantValue.OK_MESSAGE);
      boardResponse.setMessage(ConstantValue.OK_MESSAGE);
    } catch (Exception e) {
      log.error("[BoardServiceImpl-getBoard]" + e.getMessage());
      boardResponse.setResult(ConstantValue.ERR_RESULT);
      boardResponse.setMessage(ConstantValue.ERR_MESSAGE);
    }

    return boardResponse;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResultDomain insertReply(Board board) throws Exception {
    ResultDomain result = new ResultDomain();
    try {
      // 답글 등록을 위해 원글의 bgroup, 원글의 sorts, 원글의 depth를 찾아온다.
      Board parentInfo = boardMapper.getBoard(board.getInsertParam().getParentNo());
      // 원본 게시글이 삭제 되었을 경우
      if (parentInfo == null) {
        result.setResult(ConstantValue.ERR_RESULT);
        result.setMessage(ConstantValue.DELETE_ORIGINAL_POST);
      } else {
        // 원본 게시글이 존재 할 경우
        int sorts = boardMapper.replyFindSorts(parentInfo);
        // sorts에 따라 분기
        if (sorts == ConstantValue.SORTS) {
          int replySorts = boardMapper.findSorts(parentInfo.getReGroup());
          // insert reply (board, parent reGroup, replySorts, parent depth +1)
          board.setReSorts(replySorts);
        } else {
          // update sorts
          boardMapper.updateSorts(parentInfo.getReGroup(), sorts);
          // insert reply (parent reGroup, sorts, parent depth +1)
          board.setReSorts(sorts);
        }

        board.setReGroup(parentInfo.getReGroup());
        board.setReDepth(parentInfo.getReDepth() + ConstantValue.ONE);
        board.setPassword(BoardUtils.encryption(board.getPassword()));
        boardMapper.insertBoard(board);

        // 파일 존재 여부 확인 후 첨부파일 저장
        if (!StringUtils.equals(board.getInsertParam().getFileName(), "")) {
          AttachBoard attachBoard = BoardUtils.uploadImage(board.getInsertParam().getAttach(), board.getInsertParam().getFileName());
          attachBoard.setBoardNo(board.getBoardNo());
          String[] attachArr = attachBoard.getAttachPath().split(ConstantValue.SLASH);
          attachBoard.setUrlPath(ConstantValue.PATH + ConstantValue.SLASH + attachArr[4] + ConstantValue.SLASH + attachArr[5] + ConstantValue.SLASH + attachArr[6] + ConstantValue.SLASH + attachBoard.getFileName());
          result = insertAttach(attachBoard);
        }

        result.setResult(ConstantValue.OK_MESSAGE);
        result.setMessage(ConstantValue.OK_MESSAGE);
      }
    } catch (Exception e) {
      result.setResult(ConstantValue.ERR_RESULT);
      result.setMessage(ConstantValue.ERR_MESSAGE);
      log.error("[BoardServiceImpl - insertReply] ==> " + e.getMessage());
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // rollback
    }
    return result;
  }

  @Override
  public BoardResponse getSearchList(SearchParam searchValue) throws Exception {
    BoardResponse boardResponse = new BoardResponse();
    try {
      boardResponse.setBoardList(boardMapper.getSearchList(searchValue));
      boardResponse.setResult(ConstantValue.OK_MESSAGE);
      boardResponse.setMessage(ConstantValue.OK_MESSAGE);
    } catch (Exception e) {
      boardResponse.setResult(ConstantValue.ERR_RESULT);
      boardResponse.setMessage(ConstantValue.ERR_MESSAGE);
      log.error("[BoardServiceImpl - getSearchList] ==> " + e.getMessage());
    }
    return boardResponse;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResultDomain modifyBoard(Board board) throws Exception {
    ResultDomain resultDomain = new ResultDomain();
    try {
      // 분기 이미지가 존재 하느냐 아니냐에 따라 첨부파일 업데이트도 진행 해야 함.
      board.setPassword(BoardUtils.encryption(board.getPassword()));
      boardMapper.modifyBoard(board);
      // 파일을 삭제 한 경우
      if (StringUtils.equals(board.getInsertParam().getAttach(), "")) {
        attachMapper.deleteAttach(board.getBoardNo());
      }
      // 파일을 새로 업로드 한 경우
      if (board.getInsertParam().getFileName() != null) {
        AttachBoard attachBoard = BoardUtils.uploadImage(board.getInsertParam().getAttach(), board.getInsertParam().getFileName());
        // 지금은 게시물 1개당 첨부파일 1개이기에 1:1 관계라 boardNo 가능 -> 만약 1:N일 경우라면 유니크한 값 으로
        attachBoard.setBoardNo(board.getBoardNo());
        String[] attachArr = attachBoard.getAttachPath().split(ConstantValue.SLASH);
        attachBoard.setUrlPath(ConstantValue.PATH + ConstantValue.SLASH + attachArr[4] + ConstantValue.SLASH + attachArr[5] + ConstantValue.SLASH + attachArr[6] + ConstantValue.SLASH + attachBoard.getFileName());
        String result = attachMapper.findAttachNo(board.getBoardNo());
        if (result == null) {
          attachMapper.insertAttach(attachBoard);
        } else {
          attachBoard.setAttachNo(Integer.parseInt(result));
          attachMapper.modifyAttach(attachBoard);
        }
      }
      resultDomain.setResult(ConstantValue.OK_MESSAGE);
      resultDomain.setMessage(ConstantValue.OK_MESSAGE);
    } catch (Exception e) {
      resultDomain.setResult(ConstantValue.ERR_RESULT);
      resultDomain.setMessage(ConstantValue.ERR_MESSAGE);
      log.error("[BoardServiceImpl - modifyBoard ==> ]" + e.getMessage());
      e.printStackTrace();
    }
    return resultDomain;
  }

  @Override
  public ResultDomain passConfirm(Board board) throws Exception {
    ResultDomain resultDomain = new ResultDomain();
    try {
      String dbPass = boardMapper.findPassByBoardNo(board);

      if (dbPass == null) {
        // 답글의 수정, 삭제 경우 confirm을 체크할 때, 원본 게시글이 삭제되 dbPass가 NULL이 될 경우
        resultDomain.setResult(ConstantValue.ERR_RESULT);
        resultDomain.setMessage(ConstantValue.DELETE_ORIGINAL_POST);
      } else {
        String encryptPass = BoardUtils.encryption(board.getPassword());
        if (StringUtils.equals(dbPass, encryptPass)) {
          resultDomain.setResult(ConstantValue.OK_MESSAGE);
          resultDomain.setMessage(ConstantValue.OK_MESSAGE);
        } else {
          resultDomain.setResult(ConstantValue.PASS_DIFFERENT_RESULT);
          resultDomain.setMessage(ConstantValue.PASS_DIFFERENT_MESSAGE);
        }
      }

    } catch (Exception e) {
      resultDomain.setResult(ConstantValue.ERR_RESULT);
      resultDomain.setMessage(ConstantValue.ERR_MESSAGE);
      log.error("[BoardServiceImpl - passConfirm ==> ]" + e.getMessage());
    }
    return resultDomain;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public ResultDomain removeBoard(int boardNo) throws Exception {
    ResultDomain resultDomain = new ResultDomain();
    try {
      DetailBoard tempBoard = boardMapper.getBoard(boardNo);
      // re_group 번호가 boardNo와 다르다면 답글임으로 -> 원글이 삭제되었습니다 표기
      if (tempBoard.getReGroup() != boardNo) {
        boardMapper.removeUpdateBoard(boardNo);
        if (StringUtils.equals(tempBoard.getUrlPath(), null)) {
          attachMapper.deleteAttach(boardNo);
        }
        int re_group = boardMapper.getReGroupByBoardNo(boardNo);
        if(deleteReGroup(re_group)){
          boardMapper.removeBoard(re_group, 2);
        }
      } else {
        // boardNo와 re_group이 같은 경우, 테이블 내에 나의 board_no를 참조하는 데이터가 있는지 check
        int result = boardMapper.getReGroupCount(boardNo);
        if (result == ConstantValue.ONE) {
          // 1인 경우 답글이 미존재 -> 본 글 삭제
          boardMapper.removeBoard(boardNo, 1);
        } else {
          boardMapper.removeUpdateBoard(boardNo);
          if (StringUtils.equals(tempBoard.getUrlPath(), null)) {
            attachMapper.deleteAttach(boardNo);
          }
          int re_group = boardMapper.getReGroupByBoardNo(boardNo);
          if(deleteReGroup(re_group)){
            boardMapper.removeBoard(re_group, 2);
          }
        }
      }
      resultDomain.setResult(ConstantValue.OK_MESSAGE);
      resultDomain.setMessage(ConstantValue.OK_MESSAGE);
    } catch (Exception e) {
      resultDomain.setResult(ConstantValue.ERR_RESULT);
      resultDomain.setMessage(ConstantValue.ERR_MESSAGE);
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // rollback
    }
    return resultDomain;
  }

  private boolean deleteReGroup(int reGroup) throws Exception{
    return boardMapper.deleteList(reGroup).stream().filter(board -> !StringUtils.equals(board.getTitle(), "삭제 된 글입니다.")).collect(Collectors.toList()).size() == ConstantValue.SORTS;
  }

}
