package com.blue.bluearchive.shop_inquiry.controller;

import com.blue.bluearchive.shop_inquiry.service.InquiryAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/item/inquiryAnswer")
@RequiredArgsConstructor
public class inquiryAnswerController {

    private final InquiryAnswerService inquiryAnswerService;

    @PostMapping("/{inquiryId}")
    public String submitAnswer(@PathVariable("inquiryId")Long inquiryId, HttpServletRequest request, RedirectAttributes attributes) {
        String answerContent = request.getParameter("answerContent");
        String itemId = request.getParameter("itemId");

        if (answerContent == null || answerContent.trim().isEmpty()) {
            attributes.addFlashAttribute("error", "답변 내용을 입력해주세요.");
            return "redirect:/member/item/" + itemId;
        } else {
            inquiryAnswerService.answerWrite(inquiryId, answerContent);
            attributes.addFlashAttribute("message", "문의가 성공적으로 등록되었습니다.");
            return "redirect:/member/item/" + itemId;
        }

    }

    @PostMapping("/delete/{inquiryAnswerId}")
    public ResponseEntity<String> deleteAnswer(@PathVariable("inquiryAnswerId") Long inquiryAnswerId) {
        inquiryAnswerService.answerDelete(inquiryAnswerId);
        return ResponseEntity.ok("답변이 성공적으로 삭제되었습니다.");
    }

    @PostMapping("/update/{inquiryAnswerId}")
    @ResponseBody
    public ResponseEntity<String> updateAnswer(@RequestParam("id") Long id, @RequestParam("content") String content) {
        if (content == null || content.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("수정할 내용을 입력해주세요.");
        }
        try {
            inquiryAnswerService.answerUpdate(id, content);
            return ResponseEntity.ok("답변이 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("답변 수정 중 오류가 발생했습니다.");
        }
    }
}
