package com.blue.bluearchive.shop.service;

import com.blue.bluearchive.constant.Grade;
import com.blue.bluearchive.constant.OrderSellerStatus;
import com.blue.bluearchive.member.entity.Member;
import com.blue.bluearchive.member.repository.MemberRepository;
import com.blue.bluearchive.shop.dto.OrderDto;
import com.blue.bluearchive.shop.entity.Item;
import com.blue.bluearchive.shop.entity.Order;
import com.blue.bluearchive.shop.entity.OrderItem;
import com.blue.bluearchive.shop.repository.ItemImgRepository;
import com.blue.bluearchive.shop.repository.ItemRepository;
import com.blue.bluearchive.shop.repository.OrderItemRepository;
import com.blue.bluearchive.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemImgRepository itemImgRepository;
    private final OrderItemRepository orderItemRepository;
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public Long order(OrderDto orderDto, String email) {
        Item item = itemRepository.findById(orderDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);
        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);
        return order.getId();
    }


    @Transactional(readOnly = true)
    public List<OrderItem> getItemBuyerHistPage(String idx) {
        return orderItemRepository.findByCreatedBy(idx);

//        List<OrderHistDto> orderHistDtos = new ArrayList<>();
//
//        for (Order order : orders) {
//            OrderHistDto orderHistDto = new OrderHistDto(order);
//            List<OrderItem> orderItems = order.getOrderItems();
//            for (OrderItem orderItem : orderItems) {
//                ItemImg itemImg = itemImgRepository.findByItemIdAndRepimgYn(
//                        orderItem.getItem().getId(), "Y");
//                OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getImgUrl());
//                orderHistDto.addOrderItemDto(orderItemDto);
//            }
//            orderHistDtos.add(orderHistDto);
//        }
//
//        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        order.cancelOrder();

        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderSellerStatus(OrderSellerStatus.CANCEL);
            orderItemRepository.save(orderItem);
        }
    }

    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String email) {
        Member curMember = memberRepository.findByEmail(email);
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        Member savedMember = order.getMember();
        if (!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) {
            return false;
        }
        return true;
    }

    public Long orders(List<OrderDto> orderDtoList, String email) {
        Member member = memberRepository.findByEmail(email);
        List<OrderItem> orderItemList = new ArrayList<>();

        for (OrderDto orderDto : orderDtoList) {
            Item item = itemRepository.findById(orderDto.getItemId())
                    .orElseThrow(EntityNotFoundException::new);
            OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
            orderItemList.add(orderItem);
        }
        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);
        return order.getId();
    }

    public void checkGrade(String email) {
        Member member = memberRepository.findByEmail(email);
        Integer sumPrice = orderItemRepository.calculateTotalPriceByCreatedByAndStatus(String.valueOf(member.getIdx()));


        if (sumPrice != null) {
            if (sumPrice > 10000) {
                member.setGrade(Grade.GOLD);
            } else if (sumPrice > 100) {
                member.setGrade(Grade.SILVER);
            } else {
                member.setGrade(Grade.BRONZE);
            }
        } else {
            member.setGrade(Grade.BRONZE);
        }

        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public Integer getBuyItemNum(String createBy) {
        return orderItemRepository.countByCreatedBy(createBy);
    }



}