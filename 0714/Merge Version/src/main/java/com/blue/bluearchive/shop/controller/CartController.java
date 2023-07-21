package com.blue.bluearchive.shop.controller;

import com.blue.bluearchive.member.dto.CustomUserDetails;
import com.blue.bluearchive.member.entity.Member;
import com.blue.bluearchive.member.repository.MemberRepository;
import com.blue.bluearchive.shop.dto.CartDetailDto;
import com.blue.bluearchive.shop.dto.CartItemDto;
import com.blue.bluearchive.shop.dto.CartOrderDto;
import com.blue.bluearchive.shop.service.CartService;
import com.blue.bluearchive.shop.service.OrderService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.List;

@Controller
@RequestMapping("/shop")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;
    private final OrderService orderService;
    private final MemberRepository memberRepository;
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @PostMapping(value = "/cart")
    public @ResponseBody ResponseEntity addCart(@RequestBody @Valid CartItemDto cartItemDto,
                                                BindingResult bindingResult, Principal principal) {
        //Email 값 불러오기 위해서 만든 것
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        Object principalCus = auth.getPrincipal();
        CustomUserDetails userDetails = (CustomUserDetails) principalCus;
        String email = userDetails.getEmail();

        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        Long cartItemId;
        try {
            cartItemId = cartService.addCart(cartItemDto, email);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }


    @GetMapping(value = "/cart")
    public String cartHist(Principal principal, Model model) {

        //Email 값 불러오기 위해서 만든 것
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        Object principalCus = auth.getPrincipal();
        CustomUserDetails userDetails = (CustomUserDetails) principalCus;

        String email = userDetails.getEmail();

        Member member = memberRepository.findByEmail(email);
        String grade = member.getGrade().toString();
        model.addAttribute("grade", grade);

        List<CartDetailDto> cartDetailDtoList = cartService.getCartList(email);
        model.addAttribute("cartItems", cartDetailDtoList);
        return "shop/shopCart";
    }

    @PatchMapping(value = "/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity updateCartItem(@PathVariable("cartItemId")  Long cartItemId,
                                                                                    int count,
                                                                                    Principal principal)
    {



        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        Object principalCus = auth.getPrincipal();
        CustomUserDetails userDetails = (CustomUserDetails) principalCus;
        String email = userDetails.getEmail();

        if (count <= 0) {
            return new ResponseEntity<String>("최소 1개 이상 담아주세요.", HttpStatus.BAD_REQUEST);
        } else if (!cartService.validateCartItem(cartItemId, email)) {
            return new ResponseEntity<String>("수정권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        cartService.updateCartItemCount(cartItemId, count);
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    @DeleteMapping(value = "/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity deleteCartItem(@PathVariable("cartItemId") Long cartItemId, Principal principal) {

        //Email 값 불러오기 위해서 만든 것
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        Object principalCus = auth.getPrincipal();
        CustomUserDetails userDetails = (CustomUserDetails) principalCus;
        String email = userDetails.getEmail();

        if (!cartService.validateCartItem(cartItemId, email)) {
            return new ResponseEntity<String>("수정 권한이 없습니다", HttpStatus.FORBIDDEN);
        }
        cartService.deleteCartItem(cartItemId);
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }


    @PostMapping(value = "/cart/orders")
    public @ResponseBody ResponseEntity orderCartItem(@RequestBody CartOrderDto cartOrderDto, Principal principal) {
        //Email 값 불러오기 위해서 만든 것
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        Object principalCus = auth.getPrincipal();
        CustomUserDetails userDetails = (CustomUserDetails) principalCus;
        String email = userDetails.getEmail();

        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();
        if (cartOrderDtoList == null || cartOrderDtoList.size() == 0) {
            return new ResponseEntity<String>("주문할 상품을 선택하세요.", HttpStatus.FORBIDDEN);
        }
        for (CartOrderDto cartOrder : cartOrderDtoList) {
            if (!cartService.validateCartItem(cartOrder.getCartItemId(), email)) {
                return new ResponseEntity<String>("주문 권한이 없습니다.", HttpStatus.FORBIDDEN);
            }
        }

        orderService.checkGrade(email);
        Long orderId = cartService.orderCartItem(cartOrderDtoList, email);
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

}