package com.blue.bluearchive.shop.repository;

import com.blue.bluearchive.shop.dto.ItemDto;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashMap;

@Repository
public class ItemRepositoryFormImpl implements ItemRepositoryForm {
    private Long id=0L;
    private HashMap<Long,ItemDto> itemMap;

    @Override
    public void createItem(ItemDto item) {
        item.setId(++id);
        itemMap.put(id,item);
    }
    @Override
    public ItemDto getItem(Long id) {
        return itemMap.get(id);
    }
    @Override
    public HashMap<Long, ItemDto> getItemAll() {
        return itemMap;
    }
    @Override
    public Long updateItem(ItemDto item) {
        Long id=item.getId();
        itemMap.put(id,item);
        return id;
    }
    @Override
    public void deleteItem(Long id) {
        itemMap.remove(id);
    }
    public Long getCurrId(){
        return id;
    }
}
