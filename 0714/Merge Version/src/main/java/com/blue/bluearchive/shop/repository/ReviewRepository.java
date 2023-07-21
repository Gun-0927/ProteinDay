package com.blue.bluearchive.shop.repository;

import com.blue.bluearchive.shop.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    List<Review> findByItemId(Long itemId);

    //  리뷰는 한번만 쓸 수 있도록 만드는 JPA
    boolean existsByCreatedByAndItemId(@Param("createdBy") String createdBy, @Param("itemId") Long itemId);


}
