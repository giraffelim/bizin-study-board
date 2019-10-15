package com.boneis.board.task;

import com.boneis.board.domain.board.AttachBoard;
import com.boneis.board.domain.common.ConstantValue;
import com.boneis.board.mapper.AttachMapper;
import com.boneis.board.mapper.BoardMapper;
import com.boneis.board.util.BoardUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class FileRemoveTask {

  @Autowired
  BoardMapper boardMapper;
  @Autowired
  AttachMapper attachMapper;

  private String getFolderYesterDay(){
    SimpleDateFormat sdf = new SimpleDateFormat(ConstantValue.DATE_FORMAT);
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, ConstantValue.negativeOne);
    String str = sdf.format(cal.getTime());
    return str.replace(ConstantValue.FILE_REPLACE_WORD, File.separator);
  }

  @Scheduled(cron = "0 26 10 * * *")
  // cron 앞에서부터 초, 분, 시간, 일, 달, 요일, 년도
  public void removeFiles(){
    try {
      List<AttachBoard> fileList = attachMapper.getOldFiles(BoardUtils.uploadPath+ConstantValue.SLASH+getFolderYesterDay());
      // 가져온 리스트의 stream을 생성한 뒤 map을 통해 경로를 얻어온다.
      List<Path> filePathList=fileList.stream().map(vo -> Paths.get(vo.getAttachPath(), vo.getFileName())).collect(Collectors.toList());
      // 경로 파일 객체를 만든다.
      File targetDir = Paths.get(BoardUtils.uploadPath+ConstantValue.SLASH+getFolderYesterDay()).toFile();
      log.info(targetDir.getName());
      log.info(String.valueOf(targetDir.toPath()));
      // img가 DB에 존재하는지 유무 체크 후 없다면 removeFiles[]로
      File[] removeFiles = targetDir.listFiles(file -> filePathList.contains(file.toPath()) == false);
      // forEach문을 통해 반복하며 삭제
      for(File file : removeFiles){
        log.warn(file.getAbsolutePath());
        log.warn(file.getName());
        file.delete();
      }
    } catch (Exception e) {
      log.error("[FileRemoveTask ==> ]"+e.getMessage());
    }
  }

}
