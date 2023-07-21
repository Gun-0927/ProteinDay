package com.blue.bluearchive.shop.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.blue.bluearchive.shop.dto.QShopOrderDto is a Querydsl Projection type for ShopOrderDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QShopOrderDto extends ConstructorExpression<ShopOrderDto> {

    private static final long serialVersionUID = 2082507457L;

    public QShopOrderDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> itemNm, com.querydsl.core.types.Expression<String> imgUrl, com.querydsl.core.types.Expression<java.time.LocalDateTime> regTime, com.querydsl.core.types.Expression<Integer> price, com.querydsl.core.types.Expression<Integer> stockNum, com.querydsl.core.types.Expression<Integer> totalPrice, com.querydsl.core.types.Expression<String> sellStatus) {
        super(ShopOrderDto.class, new Class<?>[]{long.class, String.class, String.class, java.time.LocalDateTime.class, int.class, int.class, int.class, String.class}, id, itemNm, imgUrl, regTime, price, stockNum, totalPrice, sellStatus);
    }

}

