package com.blue.bluearchive.shop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShopOrderDto {
    private Long id;
    private String itemNm;
    private String imgUrl;
    private LocalDateTime regTime;
    private Integer price;
    private Integer stockNum;
    private Integer totalPrice;
    private String sellStatus;

    @QueryProjection
    public ShopOrderDto(Long id, String itemNm, String imgUrl, LocalDateTime regTime, Integer price, Integer stockNum, Integer totalPrice, String sellStatus){
        this.id = id;
        this.itemNm = itemNm;
        this.imgUrl = imgUrl;
        this.regTime = regTime;
        this.price = price;
        this.stockNum = stockNum;
        this.totalPrice = totalPrice;
        this.sellStatus = sellStatus;
    }


}
