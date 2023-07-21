package com.blue.bluearchive.shop.repository;

import com.blue.bluearchive.shop.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderItemRepositoryCustom {

    Page<OrderItem> getItemSellerHistPage(String itemSellerPersonIdx, Pageable pageable);

    Page<OrderItem> getItemBuyerHistPage(String createBy, Pageable pageable);

}
