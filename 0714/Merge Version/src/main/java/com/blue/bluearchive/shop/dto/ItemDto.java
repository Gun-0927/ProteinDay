package com.blue.bluearchive.shop.dto;

import com.blue.bluearchive.constant.ItemCategory;
import com.blue.bluearchive.constant.ItemUseability;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemDto {
    private Long id;
    private String itemNm;
    private int price;
    private String itemDetail;
    private String sellStatCd;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;
	private String sellerNickName;
    private ItemUseability itemUseability; //상품 삭제 여부 디폴트 값은 사용 가능
    private ItemCategory itemCategory;     //상품 카테고리 총 4종류 디폴트 값은 null
}
