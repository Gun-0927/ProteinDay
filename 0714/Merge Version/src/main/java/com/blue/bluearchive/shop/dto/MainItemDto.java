package com.blue.bluearchive.shop.dto;

import com.blue.bluearchive.constant.ItemCategory;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainItemDto {
    private Long id;
    private String itemNm;
    private String itemDetail;
    private String imgUrl;
    private Integer price;
    private ItemCategory itemCategory;
    private String sellerNickName;

    @QueryProjection
    public MainItemDto(Long id, String itemNm, String itemDetail, String imgUrl, Integer price, ItemCategory itemCategory, String sellerNickName) {
        this.id = id;
        this.itemNm = itemNm;
        this.itemDetail = itemDetail;
        this.imgUrl = imgUrl;
        this.price = price;
        this.itemCategory = itemCategory;
        this.sellerNickName = sellerNickName;
    }

}
