package com.boneis.board.mapper;

import com.boneis.board.domain.board.AttachBoard;
import com.boneis.board.domain.board.Board;
import com.boneis.board.domain.board.DetailBoard;
import com.boneis.board.domain.board.SearchParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface BoardMapper {

  List<Board> getAllList() throws Exception; // 전체 리스트 출력
  void insertBoard (Board board) throws Exception; // 게시글 등록
  DetailBoard getBoard(int boardNo)throws Exception; // 게시글 상세보기
  void updateReGroup (Board board) throws Exception; // 게시글 update(re_group)
  int replyFindSorts (Board board) throws Exception; // SORTS값
  int findSorts (int reGroup) throws Exception; // 답글의 sorts값
  void updateSorts (@Param("reGroup") int reGroup, @Param("reSorts") int reSorts) throws Exception;
  List<Board> getSearchList(SearchParam searchValue) throws Exception; // 검색 리스트 출력
  void modifyBoard (Board board) throws Exception; // 게시판 수정
  String findPassByBoardNo (Board board) throws Exception; // 게시글 번호로 패스워드 찾기
  int removeBoard (@Param("boardNo") int boardNo, @Param("flag") int flag) throws Exception; // 게시글 삭제
  int removeUpdateBoard (int boardNo) throws Exception; // 삭제 후 업데이트 처리
  int getReGroupCount (int boardNo) throws Exception; // getReGroup
  int getReGroupByBoardNo (int boardNo) throws Exception; // getRegroupByBoardNo
  List<Board> deleteList (int boardNo) throws Exception;
}
