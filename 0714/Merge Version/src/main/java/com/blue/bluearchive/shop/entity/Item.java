package com.blue.bluearchive.shop.entity;

import com.blue.bluearchive.constant.ItemCategory;
import com.blue.bluearchive.constant.ItemSellStatus;
import com.blue.bluearchive.constant.ItemUseability;
import com.blue.bluearchive.exception.OutOfStockException;
import com.blue.bluearchive.report_shop.entity.ReportShop;
import com.blue.bluearchive.shop.dto.ItemFormDto;
import com.blue.bluearchive.shop_inquiry.entity.Inquiry;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "item")
@Getter
@Setter
@ToString
public class Item extends BaseEntity {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //상품 코드

    @Column(nullable = false, length = 50)
    private String itemNm; //상품명

    @Column(name = "price", nullable = false)
    private int price; //가격

    @Column(nullable = false)
    private int stockNumber; //재고수량

    @Lob
    @Column(nullable = false)
    private String itemDetail; //상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;  //상품 판매 상태

    @Enumerated(EnumType.STRING)
    private ItemCategory itemCategory;      //상품 카테고리


    @Enumerated(EnumType.STRING)
    private ItemUseability itemUseability = ItemUseability.ABLE; //상품 삭제 여부 디폴트 값은 사용 가능

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inquiry> inquiry;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportShop> reportShop;


//    아이템이 지워지면 그와 관련된 이미지 정보도 같이 지워짐
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemImg> itemImg;

//    아이템이 지워지면 그와 관련된 카트에 내용물도 같이 지워짐
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems;

//    아이템이 지워지면 그와 관련된 구매이력에 내용물도 같이 지워짐
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;


    public void updateItem(ItemFormDto itemFormDto) {
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
        this.itemCategory = itemFormDto.getItemCategory();
    }

    // 재고 변경에 대한 것
    // 엔티티 내에서 하는 이유가 어짜피 감소한 값을 업데이트 해도 되지만 
    // 여기서 하면 공용으로 있기에 구현 과정이 편해짐 예외처리는 OutOfStockException에서 처리할 예정
    public void removeStock(int sellNumber) {
        if (this.stockNumber == 0) {
            throw new OutOfStockException(this.itemNm + "은 판매 불가 상품입니다)");
        }
        int restStock = this.stockNumber - sellNumber;
        // 남은 재고가 0개가 되면 품절 상태로 수정
        if (restStock == 0) {
            this.itemSellStatus = ItemSellStatus.SOLD_OUT;
        } else if (restStock < 0) {
            throw new OutOfStockException(this.itemNm + "의 상품의 재고가 부족합니다.(현재 재고 수량: " + this.stockNumber + ")");
        }
        this.stockNumber = restStock;
    }


    public void addStock(int cancelNumber) {
        // 품절상태에서 주문 취소하면 다시 판매 가능 상태로 전환
        if (this.itemSellStatus == ItemSellStatus.SOLD_OUT) {
            this.itemSellStatus = ItemSellStatus.SELL;
        }
        this.stockNumber += cancelNumber;
    }

}
