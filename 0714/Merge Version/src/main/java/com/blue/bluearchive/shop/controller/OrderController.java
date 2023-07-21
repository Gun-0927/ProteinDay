package com.blue.bluearchive.shop.controller;

import com.blue.bluearchive.member.dto.CustomUserDetails;
import com.blue.bluearchive.member.repository.MemberRepository;
import com.blue.bluearchive.shop.dto.OrderDto;
import com.blue.bluearchive.shop.entity.Item;
import com.blue.bluearchive.shop.entity.ItemImg;
import com.blue.bluearchive.shop.entity.OrderItem;
import com.blue.bluearchive.shop.repository.ItemImgRepository;
import com.blue.bluearchive.shop.repository.ItemRepository;
import com.blue.bluearchive.shop.repository.OrderItemRepository;
import com.blue.bluearchive.shop.service.ItemService;
import com.blue.bluearchive.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/shop")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final OrderItemRepository orderItemRepository;
    private final ItemService itemService;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // Ajax 쓰는 부분
    @PostMapping(value = "/order")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDto orderDto,
                                              BindingResult bindingResult,
                                              Principal principal) { // 여기서 email을 받아오기에 그것 때문에
        // security의 principal를 가져오고 쓴다
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        Object principalCus = auth.getPrincipal();
        CustomUserDetails userDetails = (CustomUserDetails) principalCus;

        String email = userDetails.getEmail();
        Long orderId;
        try {
            orderId = orderService.order(orderDto, email);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        orderService.checkGrade(userDetails.getEmail());
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

    @GetMapping(value = {"/orders", "/orders/{page}"})
    public String orderHist(@PathVariable("page") Optional<Integer> page,Model model) {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        Object principalCus = auth.getPrincipal();
        CustomUserDetails userDetails = (CustomUserDetails) principalCus;

        int pageNumber = page.orElse(0); // page 값이 없을 경우 기본값으로 0을 할당
        Pageable pageable = PageRequest.of(pageNumber, 5);

        Page<OrderItem> orderItems = itemService.getItemBuyerHistPage(userDetails.getIdx().toString(),pageable);
        List<String> itemImgList = new ArrayList<>();
        List<String> itemNms = new ArrayList<>();


        for (OrderItem orderItem : orderItems) {
            ItemImg itemImg = itemImgRepository.findByItemIdAndRepimgYn( orderItem.getItem().getId(),"Y");
            Optional<Item> item = itemRepository.findById(orderItem.getItem().getId());

            itemImgList.add(itemImg.getImgUrl());
            itemNms.add(item.get().getItemNm());
        }
        model.addAttribute("orderItems", orderItems);
        model.addAttribute("itemImgList", itemImgList);
        model.addAttribute("itemNms", itemNms);
        model.addAttribute("maxPage", 5);

        return "shop/shopOrder";
    }

    @PostMapping("/order/{orderId}/cancel")
    public @ResponseBody ResponseEntity cancelOrder(@PathVariable("orderId") Long orderId, Principal principal) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        Object principalCus = auth.getPrincipal();
        CustomUserDetails userDetails = (CustomUserDetails) principalCus;


        if (!orderService.validateOrder(orderId, userDetails.getEmail())) {
            return new ResponseEntity<String>("주문 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        orderService.cancelOrder(orderId);
        orderService.checkGrade(userDetails.getEmail());
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

}
