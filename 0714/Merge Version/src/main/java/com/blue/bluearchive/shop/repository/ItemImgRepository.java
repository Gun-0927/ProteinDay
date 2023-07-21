package com.blue.bluearchive.shop.repository;

import com.blue.bluearchive.shop.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg,Long> {
    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);
    void deleteByItemId(Long itemId);
    ItemImg findByItemIdAndRepimgYn(Long itemId, String repimgYn);
}
