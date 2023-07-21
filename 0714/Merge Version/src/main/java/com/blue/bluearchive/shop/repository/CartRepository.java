package com.blue.bluearchive.shop.repository;

import com.blue.bluearchive.shop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByMemberIdx(Long memberIdx);
}
