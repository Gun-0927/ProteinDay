package com.blue.bluearchive.shop.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.blue.bluearchive.shop.dto.QMainItemDto is a Querydsl Projection type for MainItemDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMainItemDto extends ConstructorExpression<MainItemDto> {

    private static final long serialVersionUID = -1061812071L;

    public QMainItemDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> itemNm, com.querydsl.core.types.Expression<String> itemDetail, com.querydsl.core.types.Expression<String> imgUrl, com.querydsl.core.types.Expression<Integer> price, com.querydsl.core.types.Expression<com.blue.bluearchive.constant.ItemCategory> itemCategory, com.querydsl.core.types.Expression<String> sellerNickName) {
        super(MainItemDto.class, new Class<?>[]{long.class, String.class, String.class, String.class, int.class, com.blue.bluearchive.constant.ItemCategory.class, String.class}, id, itemNm, itemDetail, imgUrl, price, itemCategory, sellerNickName);
    }

}

