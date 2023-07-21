package com.blue.bluearchive.shop.repository;

import com.blue.bluearchive.shop.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, QuerydslPredicateExecutor<OrderItem>, OrderItemRepositoryCustom {

    @Query("SELECT SUM(oi.orderPrice * oi.count) FROM OrderItem oi " +
            "WHERE oi.createdBy = :createdBy " +
            "AND oi.orderSellerStatus IN ('ORDERING', 'DELIVERING', 'DELIVERED')")
    Integer calculateTotalPriceByCreatedByAndStatus(@Param("createdBy") String createdBy);


    // 배송 완료된 사람만 리뷰 쓸 수 있도록 만드는 JPA
    @Query("SELECT CASE WHEN COUNT(oi) > 0 THEN true ELSE false END FROM OrderItem oi " +
            "WHERE oi.createdBy = :createdBy " +
            "AND oi.item.id = :itemId " +
            "AND oi.orderSellerStatus = 'DELIVERED'")
    boolean existsByCreatedByAndItemIdAndStatusDelivered(@Param("createdBy") String createdBy, @Param("itemId") Long itemId);


    List<OrderItem> findAll();

    List<OrderItem> findByCreatedBy(String createdBy);

    Integer countByCreatedBy(String createdBy);

}
