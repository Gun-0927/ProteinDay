package com.blue.bluearchive.board.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@Log
public class FileService {

    public String uploadFile(String uploadPath,String originalFileName, byte[] fileData) throws Exception{
        UUID uuid = UUID.randomUUID();
        System.out.println("============uploadFile 메서드 처리진입============"+uploadPath);
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = uuid.toString()+extension;
        String fileUploadFullUrl = uploadPath+"/"+savedFileName;
        System.out.println("============파일 입력 직전============"+fileUploadFullUrl);
        try (FileOutputStream fos = new FileOutputStream(fileUploadFullUrl)) {
            fos.write(fileData);
        } catch (IOException e) {
            e.printStackTrace();
            throw e; // optionally re-throw the exception
        }
        System.out.println("============파일 입력 직후 파일데이터 출력============"+fileData);
        return savedFileName;
    }
    public void deleteFile(String filePath) throws Exception{
        File deleteFile = new File(filePath);
        if(deleteFile.exists()){
            deleteFile.delete();
            log.info("파일을 삭제했습니다.");
        }else {
            log.info("파일이 존재하지 않습니다.");
        }
    }
}
