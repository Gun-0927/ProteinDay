package com.blue.bluearchive.shop.dto;

import com.blue.bluearchive.constant.OrderStatus;
import com.blue.bluearchive.shop.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OrderHistDto {
    private Long orderId;
    private String orderDate;
    private OrderStatus orderStatus;

    public List<OrderItemDto> orderItemDtoList = new ArrayList<>();


    public void addOrderItemDto(OrderItemDto orderItemDto)
    {
        orderItemDtoList.add(orderItemDto);
    }


    public OrderHistDto(Order order)
    {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus= order.getOrderStatus();
    }

}
