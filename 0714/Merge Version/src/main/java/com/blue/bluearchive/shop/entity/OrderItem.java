package com.blue.bluearchive.shop.entity;

import com.blue.bluearchive.constant.ItemUseability;
import com.blue.bluearchive.constant.OrderSellerStatus;
import com.blue.bluearchive.member.dto.CustomUserDetails;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice; //주문 가격

    private int count; //주문 수량
    
    private int totalPrice; // 실제 구매가격

    @Enumerated(EnumType.STRING)
    private ItemUseability itemUseability; //상품 삭제 여부, 디폴트 값은 사용 가능

    @Enumerated(EnumType.STRING)
    private OrderSellerStatus orderSellerStatus; // 주문에 대한 상태, 디폴트 값은 주문 중

    private int stockNumber;

	private String addressPersonIdx;
    
    private String ItemSellerPersonIdx;



    public static OrderItem createOrderItem(Item item, int count)
    {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        Object principalCus = auth.getPrincipal();
        CustomUserDetails userDetails = (CustomUserDetails) principalCus;

        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setOrderPrice(item.getPrice());
        orderItem.setItemUseability(item.getItemUseability());
        orderItem.setStockNumber(item.getStockNumber());
        orderItem.setItemSellerPersonIdx(item.getCreatedBy());
        orderItem.setAddressPersonIdx(item.getCreatedBy());
        orderItem.setOrderSellerStatus(OrderSellerStatus.ORDERING);
        if(userDetails.getGrade().toString() == "GOLD"){
            orderItem.setTotalPrice((int)(item.getPrice()*orderItem.count*0.8));
        }else if(userDetails.getGrade().toString() == "SILVER"){
            orderItem.setTotalPrice((int)(item.getPrice()*orderItem.count*0.9));
        }else{
            orderItem.setTotalPrice(item.getPrice()*orderItem.count);
        }
        item.removeStock(count);
        return orderItem;
    }

    public void cancel()        { this.getItem().addStock(count); }

}
