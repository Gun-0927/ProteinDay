package com.blue.bluearchive.shop.service;

import com.blue.bluearchive.shop.entity.ReviewImg;
import com.blue.bluearchive.shop.repository.ReviewImgRepository;
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
public class ReviewImgService {

    @Value("${reviewImgLocation}")
    private String reviewImgLocation;

    private final ReviewImgRepository reviewImgRepository;
    private final ShopFileService shopFileService;

    public void saveReviewImg(ReviewImg reviewImg, MultipartFile reviewImgFile) throws Exception{

        String oriImgName=reviewImgFile.getOriginalFilename();
        String imgName="";
        String imgUrl="";
        //파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){
            imgName= shopFileService.uploadFile(reviewImgLocation,oriImgName, reviewImgFile.getBytes());
            // 이거 수정함 파일 위치 바꾸기 위해서
            imgUrl="/images/review/"+imgName;
        }
        //상품 이미지 정보 저장
        reviewImg.updateReviewImg(oriImgName,imgName,imgUrl);
        reviewImgRepository.save(reviewImg);
    }

    public void updateReviewImg(Long reviewImgId, MultipartFile reviewImgFile) throws Exception
    {
        if(!reviewImgFile.isEmpty())
        {
            ReviewImg savedReviewImg = reviewImgRepository.findById(reviewImgId)
                    .orElseThrow(EntityNotFoundException::new);
            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedReviewImg.getImgName()))
            {
                shopFileService.deleteFile(reviewImgLocation+"/" + savedReviewImg.getImgName());
            }
            String oriImgName = reviewImgFile.getOriginalFilename();
            String imgName = shopFileService.uploadFile(reviewImgLocation, oriImgName, reviewImgFile.getBytes());
            // 여기도 수정함 파일 위치 바꿈
            String imgUrl = "/images/review/" + imgName;
            savedReviewImg.updateReviewImg(oriImgName, imgName, imgUrl);

        }
    }

}




