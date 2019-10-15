package com.boneis.board.mapper;


import com.boneis.board.domain.board.AttachBoard;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface AttachMapper {

  List<AttachBoard> getOldFiles(String attachPath) throws Exception;
  void deleteAttach (int boardNo) throws Exception;
  void insertAttach (AttachBoard attachBoard) throws Exception; // 첨부파일 등록
  String findAttachNo (int boardNo) throws Exception; // 첨부파일 번호 찾기
  void modifyAttach (AttachBoard attachBoard) throws Exception; // 첨부파일 수정
}
