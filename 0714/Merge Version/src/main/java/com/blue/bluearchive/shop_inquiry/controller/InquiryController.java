package com.blue.bluearchive.shop_inquiry.controller;

import com.blue.bluearchive.shop_inquiry.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/item/inquiry")
public class InquiryController {

    private final InquiryService inquiryService;

    @Autowired
    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @PostMapping("/{itemId}")
    public String submitInquiry(@PathVariable("itemId") Long itemId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String email = request.getParameter("createdBy");
        String content = request.getParameter("inquiryContent");
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<" + email + content);

        if (content == null || content.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "문의 내용을 입력해주세요.");
            return "redirect:/item/" + itemId;
        } else {
            inquiryService.inquiryWrite(itemId, email, content);
            redirectAttributes.addFlashAttribute("message", "문의가 성공적으로 등록되었습니다.");
            return "redirect:/item/" + itemId;
        }
    }

    @PostMapping("/delete/{inquiryId}")
    public ResponseEntity<String> deleteInquiry(@PathVariable("inquiryId") Long inquiryId) {
        inquiryService.inquiryDelete(inquiryId);
        return ResponseEntity.ok("문의가 성공적으로 삭제되었습니다.");
    }

    @PostMapping("/update/{inquiryId}")
    @ResponseBody
    public ResponseEntity<String> updateInquiry(@RequestParam("id") Long id, @RequestParam("content") String content) {
        if (content == null || content.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("수정할 내용을 입력해주세요.");
        }
        try {
            inquiryService.inquiryUpdate(id, content);
            return ResponseEntity.ok("문의가 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("문의 수정 중 오류가 발생했습니다.");
        }
    }

}
