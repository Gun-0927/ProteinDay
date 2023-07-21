package com.blue.bluearchive.shop.dto;

import com.blue.bluearchive.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSearchDto  {

    // 상품 등록일 all 1d 1w 1m 6m
    private String searchDateType;
    private ItemSellStatus searchSellStatus;

    // 검색대상 itemNm createdBy
    private String searchBy;
    private String searchQuery="";

}
