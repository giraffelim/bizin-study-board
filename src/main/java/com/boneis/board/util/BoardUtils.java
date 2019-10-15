package com.boneis.board.util;

import com.boneis.board.domain.board.AttachBoard;
import com.boneis.board.domain.common.ConstantValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class BoardUtils {

  public static String uploadPath = "";

  @Value("${uploadPath}")
  public void setUploadPath(String value) {
    uploadPath = value;
  }

  public String getUploadPath(){
    return uploadPath;
  }

  // password hash encryption
    public static String encryption(String password){
    StringBuilder sb = new StringBuilder();
    try {
      MessageDigest digest = MessageDigest.getInstance(ConstantValue.HASH_ALGORITHM);
      digest.update(password.getBytes());
      byte[] digestArr = digest.digest();
      for (byte b : digestArr){
        sb.append(String.format("%02x",b));
      }
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return sb.toString();
  }

  // 파일을 년/월/일 단위의 폴더를 통해 관리
  public static String getFolder(){
    SimpleDateFormat sdf = new SimpleDateFormat(ConstantValue.DATE_FORMAT);
    Date date = new Date();
    String str = sdf.format(date);
    return str.replace(ConstantValue.FILE_REPLACE_WORD, File.separator);
  }

  //공통 분리
  public static AttachBoard uploadImage(String attach, String fileName) {
    // 파일 업로드 method
    // 업로드 될 경로
    String uploadFolderPath = BoardUtils.getFolder();
    // mkdir
    File uploadLocation = new File(uploadPath, uploadFolderPath);
    // 디렉토리 존재 유무 확인 -> 존재 하지 않는다면 생성
    if(!uploadLocation.exists()){
      uploadLocation.mkdirs();
    }
    // attach를 split "," 기준으로
    String[] splitArray = attach.split(ConstantValue.ATTACH_SPLIT_WORD);
    // base64 decoding
    byte[] data = DatatypeConverter.parseBase64Binary(splitArray[1]);

    // 파일 이름 중복 처리를 위해 UUID값 적용
    UUID uuid = UUID.randomUUID();
    // 파일 이름 앞에 UUID를 붙이기 위한 StringBuilder
    StringBuilder sb = new StringBuilder();
    // 파일 이름 앞에 UUID 문자열 적용
    sb.append(uuid.toString()).append(ConstantValue.UNDER_BAR).append(fileName);
    //DB insert를 위한 AttachBoard setter
    AttachBoard attachBoard = new AttachBoard();
    attachBoard.setAttachPath(uploadLocation.toString());
    attachBoard.setFileName(sb.toString());

    try {
      //파일 저장
      Files.copy(new ByteArrayInputStream(data), Paths.get(uploadLocation.toString()).resolve(sb.toString()));
    } catch (IOException e) {
      e.printStackTrace();
    }

    return attachBoard;
  }

}
