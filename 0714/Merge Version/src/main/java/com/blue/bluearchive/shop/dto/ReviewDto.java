package com.blue.bluearchive.shop.dto;

import com.blue.bluearchive.shop.entity.Review;
import com.blue.bluearchive.shop.entity.ReviewImg;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ReviewDto {

    private Long id;
    private Long itemId;
    private String content;
    private String createdBy;
    private String nickName;
    private String star;
    private LocalDateTime regTime;
    private List<ReviewImg> reviewImgDtoList;


    public static ReviewDto fromEntity(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setItemId(review.getItem().getId());
        reviewDto.setContent(review.getContent());
        reviewDto.setCreatedBy(review.getCreatedBy());
        reviewDto.setNickName(review.getNickName());
        reviewDto.setStar(review.getStar());
        reviewDto.setRegTime(review.getRegTime());
        reviewDto.setReviewImgDtoList(review.getReviewImgs());
        return reviewDto;
    }
}
