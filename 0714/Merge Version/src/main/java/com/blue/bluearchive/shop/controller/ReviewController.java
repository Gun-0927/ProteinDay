package com.blue.bluearchive.shop.controller;

import com.blue.bluearchive.member.dto.CustomUserDetails;
import com.blue.bluearchive.shop.repository.OrderItemRepository;
import com.blue.bluearchive.shop.repository.ReviewRepository;
import com.blue.bluearchive.shop.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/item/review")
@RequiredArgsConstructor
public class ReviewController {

    @Autowired
    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;

    @Autowired
    private final OrderItemRepository orderItemRepository;

    @PostMapping("/{itemId}")
    public String submitReview(@PathVariable("itemId") Long itemId,
                               Model model, MultipartHttpServletRequest req,
                               @RequestParam("reviewImgFile") List<MultipartFile> reviewImgFileList,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttributes) throws Exception {

        List<MultipartFile> reviewImgFile = new ArrayList<>();
        MultiValueMap<String, MultipartFile> files = req.getMultiFileMap();
        for (Map.Entry<String, List<MultipartFile>> entry : files.entrySet()) {
            List<MultipartFile> fileList = entry.getValue();
            for (MultipartFile file : fileList) {
                if (!file.isEmpty()) {
                    reviewImgFile.add(file);
                }
            }
        }

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        Object principalCus = auth.getPrincipal();
        CustomUserDetails userDetails = (CustomUserDetails) principalCus;

        Long idx = userDetails.getIdx();
        String content = request.getParameter("reviewContent");
        String email = request.getParameter("createdByEmail");

        String star = request.getParameter("reviewStar");
        if (star == null || star.trim().isEmpty()) {
            star = "0";
        }

        if (content == null || content.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "리뷰 내용을 입력해주세요.");
            return "redirect:/item/" + itemId;
        } else if (!orderItemRepository.existsByCreatedByAndItemIdAndStatusDelivered(String.valueOf(idx), Long.valueOf(itemId))) {
            redirectAttributes.addFlashAttribute("error", "리뷰는 배송 완료 후에만 가능합니다.");
            return "redirect:/item/" + itemId;
        } else if (reviewRepository.existsByCreatedByAndItemId(String.valueOf(idx), Long.valueOf(itemId))) {
            redirectAttributes.addFlashAttribute("error", "리뷰는 한 상품에 1번만 가능합니다.");
            return "redirect:/item/" + itemId;
        } else {
            reviewService.reviewWrite(itemId, email, content, star, reviewImgFileList);
            redirectAttributes.addFlashAttribute("message", "리뷰가 성공적으로 등록되었습니다.");
            return "redirect:/item/" + itemId;
        }
    }

    @PostMapping("/delete/{inquiryId}")
    public ResponseEntity<String> deleteReview(@PathVariable("inquiryId") Long inquiryId) {
        reviewService.reviewDelete(inquiryId);
        return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다.");
    }


    @PostMapping("/update/{reviewId}")
    @ResponseBody
    public ResponseEntity<String> updateReview(@RequestParam("id") Long id,
//                                               MultipartHttpServletRequest req,
                                               @RequestParam("reviewStar") String reviewStar, HttpServletRequest request) {

        String content = request.getParameter("revContent");



        if (content == null || content.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("수정할 내용을 입력해주세요.");
        }
        try {
            reviewService.reviewUpdate(id, content, reviewStar);
            return ResponseEntity.ok("리뷰가 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 수정 중 오류가 발생했습니다.");
        }
    }



}
