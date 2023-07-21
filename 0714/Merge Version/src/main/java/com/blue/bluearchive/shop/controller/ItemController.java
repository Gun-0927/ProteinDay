package com.blue.bluearchive.shop.controller;

import com.blue.bluearchive.constant.OrderSellerStatus;
import com.blue.bluearchive.constant.ShopStatus;
import com.blue.bluearchive.member.dto.CustomUserDetails;
import com.blue.bluearchive.member.entity.Member;
import com.blue.bluearchive.member.repository.MemberRepository;
import com.blue.bluearchive.member.service.MemberService;
import com.blue.bluearchive.shop.dto.ItemFormDto;
import com.blue.bluearchive.shop.dto.ItemSearchDto;
import com.blue.bluearchive.shop.dto.ReviewDto;
import com.blue.bluearchive.shop.entity.Item;
import com.blue.bluearchive.shop.entity.ItemImg;
import com.blue.bluearchive.shop.entity.OrderItem;
import com.blue.bluearchive.shop.repository.ItemImgRepository;
import com.blue.bluearchive.shop.repository.ItemRepository;
import com.blue.bluearchive.shop.repository.OrderItemRepository;
import com.blue.bluearchive.shop.service.ItemService;
import com.blue.bluearchive.shop.service.ReviewService;
import com.blue.bluearchive.shop_inquiry.entity.Inquiry;
import com.blue.bluearchive.shop_inquiry.entity.InquiryAnswer;
import com.blue.bluearchive.shop_inquiry.service.InquiryAnswerService;
import com.blue.bluearchive.shop_inquiry.service.InquiryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
	private final InquiryService inquiryService;
	private final ReviewService reviewService;
    private final OrderItemRepository orderItemRepository;
    private final ItemImgRepository itemImgRepository;
    private final InquiryAnswerService inquiryAnswerService;
    private final ItemRepository itemRepository;


    @GetMapping(value = "/member/item/new")
    public String itemForm(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "/item/itemForm";
    }

    @PostMapping(value = "/member/item/new")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult
            , Model model
            , @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
        if (bindingResult.hasErrors()) {
            return "item/itemForm";
        }
        if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
            model.addAttribute("errorMessage"
                    , "첫번째 상품 이미지는 필수 입력 값입니다.");
            return "item/itemForm";
        }
        try {
            itemService.saveItem(itemFormDto, itemImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage"
                    , "상품 등록 중 에러가 발생했습니다.");
            return "item/itemForm";
        }
        return "redirect:/shop/main/ALL";
    }


    @GetMapping(value = "/member/item/{itemId}")
    public String itemDtl(@PathVariable("itemId") Long itemId, Model model) {
        try {
            ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
            model.addAttribute("itemFormDto", itemFormDto);

            List<Inquiry> inquiryList = inquiryService.getInquiryListByItemId(itemId);
            model.addAttribute("inquiryList", inquiryList);

            List<InquiryAnswer> inquiryAnswerList = new ArrayList<>();
            for (Inquiry inquiry : inquiryList) {
                Long inquiryId = inquiry.getId();
                List<InquiryAnswer> inquiryAnswerListForInquiry = inquiryAnswerService.getInquiryAnswerListByInquiryId(inquiryId);
                inquiryAnswerList.addAll(inquiryAnswerListForInquiry);
            }
            model.addAttribute("inquiryAnswerList", inquiryAnswerList);

            // 리뷰 목록 불러오기
            List<ReviewDto> reviewList = reviewService.getReviewListByItemId(itemId);
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication auth = context.getAuthentication();
            Object principalCus = auth.getPrincipal();
            CustomUserDetails userDetails = (CustomUserDetails) principalCus;
            Member member = memberRepository.findByEmail(userDetails.getEmail());
            for (int i = 0; i < reviewList.size(); i++) {
                ReviewDto reviewdto = reviewList.get(i);
                if (reviewdto.getCreatedBy().equals(member.getIdx().toString())) {
                    reviewList.remove(i);
                    reviewList.add(0, reviewdto);
                    break;
                }
            }
            model.addAttribute("reviewList", reviewList);   // reviewList 모델에 추가

        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
            model.addAttribute("itemFormDto", new ItemFormDto());
            return "item/itemForm";
        }
        return "item/itemMngDetail";
    }

    @PostMapping(value = "/member/item/{itemId}")
    public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                             @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList, Model model) {

        if (bindingResult.hasErrors()) {
            return "item/itemForm";
        }
        if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
            model.addAttribute("errorMessage"
                    , "첫번째 상품 이미지 는 필수 입력 값입니다.");
            return "item/itemForm";
        }
        try {
            itemService.updateItem(itemFormDto, itemImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생했습니다.");
            return "item/itemForm";
        }
        return "redirect:/shop/main/ALL";
    }

    // 아이템 삭제
    @PostMapping(value = "/member/itemDel/{itemId}")
    public String deleteItem(@PathVariable("itemId") Long itemId, Model model) {
        try {
            itemService.deleteItem(itemId);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 삭제 중 에러가 발생했습니다.");
            return "redirect:/";
        }
//        return "redirect:/shop/main/ALL";
		return "redirect:/";
    
    }


    // header에서 상품관리창
    @GetMapping(value = {"/member/items", "/member/items/{page}"})
    public String itemManage(ItemSearchDto itemSearchDto,
                             @PathVariable("page") Optional<Integer> page,
                             Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        Object principalCus = auth.getPrincipal();
        CustomUserDetails userDetails = (CustomUserDetails) principalCus;

        Member memberShopCheck = memberRepository.findByEmail(userDetails.getEmail());

        // ShopStatus 값이 SELLER일 때만 접속이 가능함
        if(memberShopCheck.getShopStatus() == ShopStatus.CONSUMER){
            model.addAttribute("isSeller", false);
            return "item/checkRole";
        } else if (memberShopCheck.getShopStatus() == ShopStatus.SELLERWAITING) {
            model.addAttribute("isSellerWaiting", true);
            model.addAttribute("alertMessage", "판매자 신청 중입니다. 승인을 기다려주세요.");
            return "item/checkRole";
        }


        List<Item> itemList = items.getContent(); // 페이지에 포함된 Item 객체 리스트를 가져옴

        // 각 Item 객체의 inquiry 리스트를 수정하여 답변이 없는 문의글만 남기기
        for (Item item : itemList) {
            List<Inquiry> inquiryList = item.getInquiry();
            inquiryList.removeIf(inquiry -> !inquiry.getInquiryAnswer().isEmpty()); // 답변이 있는 객체를 제거

            if (inquiryList.isEmpty()) {
                item.setInquiry(null); // 문의글 리스트를 비움
            }
        }


        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);
        return "item/itemMng";
    }




    //header에서 판매내역창  찾기
    @GetMapping(value = {"/member/itemSellerHist", "/member/itemSellerHist/{page}"})
    public String itemSellerHist(Model model, @PathVariable("page") Optional<Integer> page) {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        Object principalCus = auth.getPrincipal();
        CustomUserDetails userDetails = (CustomUserDetails) principalCus;

        Member memberShopCheck = memberRepository.findByEmail(userDetails.getEmail());


        // ShopStatus 값이 SELLER일 때만 접속이 가능함
        if(memberShopCheck.getShopStatus() == ShopStatus.CONSUMER){
            model.addAttribute("isSeller", false);
            return "item/checkRole";
        } else if (memberShopCheck.getShopStatus() == ShopStatus.SELLERWAITING) {
            model.addAttribute("isSellerWaiting", true);
            model.addAttribute("alertMessage", "판매자 신청 중입니다. 승인을 기다려주세요.");
            return "item/checkRole";
        }

        int pageNumber = page.orElse(0); // page 값이 없을 경우 기본값으로 0을 할당

        Pageable pageable = PageRequest.of(pageNumber, 5);
        Page<OrderItem> orderItems = itemService.getItemSellerHistPage(userDetails.getIdx().toString(),pageable);
        List<String> names = new ArrayList<>();
        List<String> addresses = new ArrayList<>();
        List<String> itemImgList = new ArrayList<>();
        List<String> itemNms = new ArrayList<>();


        for (OrderItem orderItem : orderItems) {
            Member member =  memberRepository.findByIdx(Long.valueOf(orderItem.getCreatedBy()));
            ItemImg itemImg = itemImgRepository.findByItemIdAndRepimgYn( orderItem.getItem().getId(),"Y");
            Optional<Item> item = itemRepository.findById(orderItem.getItem().getId());

            names.add(member.getNickName());
            addresses.add(member.getAddress());
            itemImgList.add(itemImg.getImgUrl());
            itemNms.add(item.get().getItemNm());
        }

        model.addAttribute("orderItems", orderItems);
        model.addAttribute("names", names);
        model.addAttribute("itemImgList", itemImgList);
        model.addAttribute("addresses", addresses);
        model.addAttribute("itemNms", itemNms);
        model.addAttribute("maxPage", 5);

        return "item/itemSellerHist";
    }

    // 아이템 상세보기창
    @GetMapping(value = "/item/{itemId}")
    public String itemDtl(Model model, @PathVariable("itemId") Long itemId) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        Object principalCus = auth.getPrincipal();
        CustomUserDetails userDetails = (CustomUserDetails) principalCus;
        Member member = memberRepository.findByEmail(userDetails.getEmail());


        String grade = (member.getGrade().toString() != null) ? member.getGrade().toString() : "BRONZE"; // 비회원은 BRONZE로 예외 처리
        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);

        //문의, 문의 답변 목록 불러오기
		List<Inquiry> inquiryList = inquiryService.getInquiryListByItemId(itemId);
		List<InquiryAnswer> inquiryAnswerList = new ArrayList<>();
        for (Inquiry inquiry : inquiryList) {
            Long inquiryId = inquiry.getId();
            List<InquiryAnswer> inquiryAnswerListForInquiry = inquiryAnswerService.getInquiryAnswerListByInquiryId(inquiryId);
            inquiryAnswerList.addAll(inquiryAnswerListForInquiry);
        }


        // 리뷰 목록 불러오기
        List<ReviewDto> reviewList = reviewService.getReviewListByItemId(itemId);
        for (int i = 0; i < reviewList.size(); i++) {
            ReviewDto reviewdto = reviewList.get(i);
            if (reviewdto.getCreatedBy().equals(member.getIdx().toString())) {
                reviewList.remove(i);
                reviewList.add(0, reviewdto);
                break;
            }
        }

        model.addAttribute("item", itemFormDto);
        model.addAttribute("grade", grade);
        model.addAttribute("inquiryList", inquiryList); // inquiryList를 모델에 추가
        model.addAttribute("reviewList", reviewList);   // reviewList 모델에 추가
        model.addAttribute("inquiryAnswerList", inquiryAnswerList);

        return "item/itemDtl";
    }

    @GetMapping(value = "/item/ordering/{orderItemId}")
    public String deliveringOrder(@PathVariable Long orderItemId, HttpServletRequest request) {

        OrderItem orderItem = orderItemRepository.findById(orderItemId).get();
        orderItem.setOrderSellerStatus(OrderSellerStatus.DELIVERING);
        orderItemRepository.save(orderItem);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping(value = "/item/cancel/{orderItemId}")
    public String cancelOrder(@PathVariable Long orderItemId, HttpServletRequest request) {

        OrderItem orderItem = orderItemRepository.findById(orderItemId).get();
        orderItem.setOrderSellerStatus(OrderSellerStatus.CANCEL);
        orderItemRepository.save(orderItem);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }



    @GetMapping(value = "/item/delivered/{orderItemId}")
    public String deliveredOrder(@PathVariable Long orderItemId, HttpServletRequest request) {

        OrderItem orderItem = orderItemRepository.findById(orderItemId).get();
        orderItem.setOrderSellerStatus(OrderSellerStatus.DELIVERED);
        orderItemRepository.save(orderItem);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }


    @GetMapping(value = "/item/refund/{orderItemId}")
    public String refundOrder(@PathVariable Long orderItemId, HttpServletRequest request) {

        OrderItem orderItem = orderItemRepository.findById(orderItemId).get();
        orderItem.setOrderSellerStatus(OrderSellerStatus.REFUND);
        orderItemRepository.save(orderItem);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping(value = "/item/refunded/{orderItemId}")
    public String refundedOrder(@PathVariable Long orderItemId, HttpServletRequest request) {

        OrderItem orderItem = orderItemRepository.findById(orderItemId).get();
        orderItem.setOrderSellerStatus(OrderSellerStatus.REFUNDED);
        orderItemRepository.save(orderItem);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PostMapping(value = "/shop/seller/{memberIdx}")
    public String sellerWaiting(@PathVariable("memberIdx") Long memberIdx, Model model) {
        try {
            memberService.applicationSellerMember(memberIdx);

        } catch (Exception e) {
            model.addAttribute("errorMessage", "판매자 신청 중 에러가 발생했습니다.");
            return "redirect:/shop/main/ALL";
        }
        return "redirect:/shop/main/ALL";
    }

    @PostMapping(value = "/admin/approval/{memberIdx}")
    public String sellerApproval(@PathVariable("memberIdx") Long memberIdx, Model model, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        try {
            memberService.approvalSellerMember(memberIdx);

        } catch (Exception e) {
            model.addAttribute("errorMessage", "에러가 발생했습니다.");
            return "redirect:" + referer;
        }
        return "redirect:" + referer;
    }

    @PostMapping(value = "/admin/approvalNot/{memberIdx}")
    public String sellerApprovalNot(@PathVariable("memberIdx") Long memberIdx, Model model, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        try {
            memberService.approvalNotMember(memberIdx);

        } catch (Exception e) {
            model.addAttribute("errorMessage", "에러가 발생했습니다.");
            return "redirect:" + referer;
        }
        return "redirect:" + referer;
    }

}
