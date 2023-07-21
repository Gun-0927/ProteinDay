package com.blue.bluearchive.shop.service;

import com.blue.bluearchive.shop.dto.ItemDto;

import java.util.HashMap;

public interface ItemServiceForm {
    void createItem(ItemDto item);
    ItemDto getItem(Long id);
    HashMap<Long,ItemDto> getItemAll();
    Long updateItem(ItemDto item);
    void deleteItem(Long id);
}
