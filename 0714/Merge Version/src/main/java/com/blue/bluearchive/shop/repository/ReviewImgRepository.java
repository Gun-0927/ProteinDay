package com.blue.bluearchive.shop.repository;

import com.blue.bluearchive.shop.entity.ReviewImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImgRepository extends JpaRepository<ReviewImg,Long> {

//    List<ReviewImg> findByReviewIdOrderByIdAsc(Long itemId);
//    void deleteByItemId(Long itemId);
//    ItemImg findByItemIdAndRepimgYn(Long itemId, String repimgYn);

    List<ReviewImg> findByReviewId(Long reviewId);


}
