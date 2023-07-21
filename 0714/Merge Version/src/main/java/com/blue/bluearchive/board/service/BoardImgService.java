package com.blue.bluearchive.board.service;

import com.blue.bluearchive.board.entity.BoardImg;
import com.blue.bluearchive.board.repository.formRepository.BoardImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardImgService {
    @Value("${boardImgLocation}")
    private String boardImgLocation;

    private final BoardImgRepository boardImgRepository;
    private final FileService fileService;

    public void saveBoardImg(BoardImg boardImg, MultipartFile boardImgFile) throws Exception{
        System.out.println("============saveBoardImg 메서드 진입============");
        String oriImgName=boardImgFile.getOriginalFilename();
        String imgName="";
        String boardImgUrl="";
        //파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){
            imgName=fileService.uploadFile(boardImgLocation,oriImgName, boardImgFile.getBytes());
            boardImgUrl="/images/board/"+imgName;

        }
        //상품 이미지 정보 저장
        boardImg.updateBoardImg(oriImgName,imgName,boardImgUrl);
        System.out.println("==============파일 저장부분 서비스===============");
        System.out.println(boardImg.getImgName()+"====="+boardImg.getBoardImgUrl());
        boardImgRepository.save(boardImg);
    }

    public void updateBoardImg(Integer boardImgId,MultipartFile boardImgFile) throws Exception{
        if(!boardImgFile.isEmpty()){
            BoardImg savedBoardImg = boardImgRepository.findById(boardImgId)
                    .orElseThrow(EntityNotFoundException::new);
            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedBoardImg.getImgName())){
                fileService.deleteFile(boardImgLocation+"/"+savedBoardImg.getImgName());
            }
            String oriImgName=boardImgFile.getOriginalFilename();
            String imgName=fileService.uploadFile(boardImgLocation,oriImgName
                    ,boardImgFile.getBytes());
            String boardImgUrl = "/images/board/"+imgName;
            savedBoardImg.updateBoardImg(oriImgName,imgName,boardImgUrl);
        }
    }
}
