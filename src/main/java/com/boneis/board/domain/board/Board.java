package com.boneis.board.domain.board;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Board {

  private int boardNo; // 게시글 번호
  private String writer; // 작성자
  private String title; // 제목
  private String password; // 비밀번호
  private String content; // 상세 내용
  private int reGroup; // 그룹 번호
  private int reSorts; // 그룹 내 순서
  private int reDepth; // 그룹 내 계층
  private Timestamp createDt; // 작성일
  private Timestamp updateDt; // 수정일
  private InsertParam insertParam; // attach, fileName, flag

}
