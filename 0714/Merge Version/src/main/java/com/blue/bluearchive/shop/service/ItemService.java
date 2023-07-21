package com.blue.bluearchive.shop.service;

import com.blue.bluearchive.constant.ItemCategory;
import com.blue.bluearchive.constant.ItemUseability;
import com.blue.bluearchive.member.entity.Member;
import com.blue.bluearchive.member.repository.MemberRepository;
import com.blue.bluearchive.shop.dto.*;
import com.blue.bluearchive.shop.entity.Item;
import com.blue.bluearchive.shop.entity.ItemImg;
import com.blue.bluearchive.shop.entity.OrderItem;
import com.blue.bluearchive.shop.repository.ItemImgRepository;
import com.blue.bluearchive.shop.repository.ItemRepository;
import com.blue.bluearchive.shop.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;
    private final MemberRepository memberRepository;
    private final OrderItemRepository orderItemRepository;


    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList)
            throws Exception {
        //상품 등록
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        //이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            if (i == 0) {
                itemImg.setRepimgYn("Y");
            } else {
                itemImg.setRepimgYn("N");
            }
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }
        return item.getId();
    }

    @Transactional(readOnly = true)
    public Optional<Item> findItem(Long itemId) {
        Optional<Item> item = itemRepository.findById(itemId);

        return item;
    }


    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long itemId) {
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for (ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByIdx(Long.valueOf(item.getCreatedBy()));
        if (item.getItemUseability() == ItemUseability.ABLE) {
            ItemFormDto itemFormDto = ItemFormDto.of(item);
            itemFormDto.setItemImgDtoList(itemImgDtoList);
            itemFormDto.setSellerNickName(member.getNickName());
            return itemFormDto;
        } else {
            return null;
        }

    }

    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
        // 상품등록
        Item item = itemRepository.findById(itemFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemFormDto);
        List<Long> itemImgIds = itemFormDto.getItemImgIds();

        // 이미지 재등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        }
        return item.getId();
    }

    public void deleteItem(Long itemId) {
        // 여기서 아이디를 기준으로 찾고 DISABLE 처리
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        item.setItemUseability(ItemUseability.DISABLE);
        itemRepository.save(item);
    }

    @Transactional(readOnly = true)
    public Integer getItemNum(String createBy) {
        return itemRepository.countByCreatedBy(createBy);
    }



    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable, ItemCategory itemCategory) {
        return itemRepository.getMainitemPage(itemSearchDto, pageable, itemCategory);
    }

    @Transactional(readOnly = true)
    public Page<OrderItem> getItemSellerHistPage(String itemSellerPersonIdx, Pageable pageable) {
        return orderItemRepository.getItemSellerHistPage(itemSellerPersonIdx, pageable);
    }

    //orderBuyer 전용 찾기
    @Transactional(readOnly = true)
    public Page<OrderItem> getItemBuyerHistPage(String itemBuyerPersonIdx, Pageable pageable) {
        return orderItemRepository.getItemBuyerHistPage(itemBuyerPersonIdx, pageable);
    }


}
