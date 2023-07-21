package com.blue.bluearchive.shop.repository;

import com.blue.bluearchive.shop.dto.CartDetailDto;
import com.blue.bluearchive.shop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long>
{
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    @Query("select new com.blue.bluearchive.shop.dto.CartDetailDto(ci.id, i.itemNm, i.price, ci.count, ii.imgUrl)"
            + " from CartItem ci, ItemImg ii join ci.item i "
            + " where ci.cart.id = :cartId "
            + " and ci.item.id = ii.item.id "
            + " and ii.repimgYn = 'Y' "
            + " and i.itemUseability = 'ABLE' " // 추가된 조건
            + " order by ci.regTime desc"
    )
    List<CartDetailDto> findCartDetailDtoList(Long cartId);

}
