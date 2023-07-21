package com.blue.bluearchive.shop.service;

import com.blue.bluearchive.member.entity.Member;
import com.blue.bluearchive.member.repository.MemberRepository;
import com.blue.bluearchive.shop.dto.ReviewDto;
import com.blue.bluearchive.shop.entity.Item;
import com.blue.bluearchive.shop.entity.Review;
import com.blue.bluearchive.shop.entity.ReviewImg;
import com.blue.bluearchive.shop.repository.ItemRepository;
import com.blue.bluearchive.shop.repository.ReviewImgRepository;
import com.blue.bluearchive.shop.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    @Autowired
    private final ReviewRepository reviewRepository;
    @Autowired
    private final ReviewImgRepository reviewImgRepository;
    @Autowired
    private final ReviewImgService reviewImgService;

    @Autowired
    private final ItemRepository itemRepository;
    @Autowired
    private final MemberRepository memberRepository;




    // 여기에 d
    @Transactional(readOnly = true)
    public List<ReviewDto> getReviewListByItemId(Long itemId) {

        List<ReviewDto> reviewDtoList = new ArrayList<>();
        List<Review> reviews = reviewRepository.findByItemId(itemId);

        for (Review review : reviews) {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setId(review.getId());
            reviewDto.setStar(review.getStar());
            reviewDto.setNickName(review.getNickName());
            reviewDto.setContent(review.getContent());

            Member member = memberRepository.findByIdx(Long.valueOf(review.getCreatedBy()));
            reviewDto.setCreatedBy(String.valueOf(member.getIdx()));
            reviewDto.setRegTime(review.getRegTime());
            reviewDto.setItemId(review.getItem().getId());

            List<ReviewImg> reviewImgList = reviewImgRepository.findByReviewId(review.getId());
            reviewDto.setReviewImgDtoList(reviewImgList);

            reviewDtoList.add(reviewDto);
        }
        return reviewDtoList;
    }

    @Transactional
    public void reviewWrite(Long itemId, String email, String content, String star, List<MultipartFile> reviewImgFileList) throws Exception {
        // Item 조회
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 아이템 ID입니다."));

        // Member 조회 여기서 구매한 손님만 쓸 수 있게 설정해야함
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new IllegalArgumentException("유효하지 않은 회원 이메일입니다.");
        }

        // Review 생성 및 저장
        Review review = new Review();
        ReviewImg reviewImg = new ReviewImg();


        if (content != null) {
            review.setStar(star);
            review.setContent(content);
            review.setItem(item);
            review.setNickName(member.getNickName());

            //이미지 등록
            for (int i = 0; i < reviewImgFileList.size(); i++) {
                ReviewImg reviewImgIn = new ReviewImg();
                reviewImgIn.setReview(review);
                reviewImgService.saveReviewImg(reviewImgIn, reviewImgFileList.get(i));
            }
        } else {
            throw new IllegalArgumentException("리뷰 내용을 입력해주세요.");
        }
        reviewRepository.save(review);
    }

    
    //리뷰 정보 수정 
    @Transactional
    public void reviewDelete(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 리뷰ID입니다."));
        reviewRepository.delete(review);
    }

    //리뷰 정보 삭제
    @Transactional
    public void reviewUpdate(Long id, String content, String star) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 리뷰ID입니다."));
        review.setContent(content);
        review.setStar(star);
    }

    @Transactional(readOnly = true)
    public ReviewDto getReviewDtl(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 리뷰ID입니다."));
        ReviewDto reviewDto = ReviewDto.fromEntity(review);
        return reviewDto;
    }

}
