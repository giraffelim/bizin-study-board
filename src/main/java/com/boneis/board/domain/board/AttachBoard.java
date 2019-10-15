package com.boneis.board.domain.board;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AttachBoard {

    private int attachNo; // 파일 번호
    private int boardNo; // 게시글 번호
    private String attachPath; // 파일 물리적 경로
    private String fileName; // 파일 이름
    private Timestamp createDt; // 등록일
    private String urlPath; // URL 경로

}
