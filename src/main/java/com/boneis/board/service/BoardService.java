package com.boneis.board.service;

import com.boneis.board.domain.board.AttachBoard;
import com.boneis.board.domain.board.Board;
import com.boneis.board.domain.ResultDomain;
import com.boneis.board.domain.board.BoardResponse;
import com.boneis.board.domain.board.SearchParam;


public interface BoardService {

  BoardResponse getAllList() throws Exception; // 게시물 목록
  ResultDomain insertBoard(Board board) throws Exception; // 게시글 등록
  ResultDomain insertAttach(AttachBoard attachBoard) throws Exception; // 첨부파일 등록
  BoardResponse getBoard(int boardNo) throws Exception; // 게시글 상세보기
  ResultDomain insertReply(Board board) throws Exception; // 답글 등록
  BoardResponse getSearchList(SearchParam searchValue) throws Exception; // 검색 리스트
  ResultDomain modifyBoard (Board board) throws Exception; // 게시판 수정 (+ 첨부파일)
  ResultDomain passConfirm (Board board) throws Exception; // password check
  ResultDomain removeBoard (int boardNo) throws Exception; // remove
}
