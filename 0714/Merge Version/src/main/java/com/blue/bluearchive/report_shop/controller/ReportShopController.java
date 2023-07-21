package com.blue.bluearchive.report_shop.controller;


import com.blue.bluearchive.report_shop.dto.ReportShopDto;
import com.blue.bluearchive.report_shop.service.ReportShopService;
import com.blue.bluearchive.shop.dto.ItemFormDto;
import com.blue.bluearchive.shop.dto.ReviewDto;
import com.blue.bluearchive.shop.entity.Item;
import com.blue.bluearchive.shop.entity.Review;
import com.blue.bluearchive.shop.repository.ItemRepository;
import com.blue.bluearchive.shop.repository.ReviewRepository;
import com.blue.bluearchive.shop.service.ItemService;
import com.blue.bluearchive.shop.service.ReviewService;
import com.blue.bluearchive.shop_inquiry.dto.InquiryAnswerDto;
import com.blue.bluearchive.shop_inquiry.dto.InquiryDto;
import com.blue.bluearchive.shop_inquiry.entity.Inquiry;
import com.blue.bluearchive.shop_inquiry.entity.InquiryAnswer;
import com.blue.bluearchive.shop_inquiry.repository.InquiryAnswerRepository;
import com.blue.bluearchive.shop_inquiry.repository.InquiryRepository;
import com.blue.bluearchive.shop_inquiry.service.InquiryAnswerService;
import com.blue.bluearchive.shop_inquiry.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/report/shop")
@RequiredArgsConstructor
public class ReportShopController {

    private final ReportShopService reportShopService;
    private final ItemService itemService;
    private final ItemRepository itemRepository;
    private final InquiryService inquiryService;
    private final InquiryAnswerService inquiryAnswerService;
    private final ReviewService reviewService;
    private final InquiryRepository inquiryRepository;
    private final InquiryAnswerRepository inquiryAnswerRepository;
    private final ReviewRepository reviewRepository;

    @GetMapping(value = "/item/{itemId}")
    public String openReportItem(@PathVariable Long itemId, Model model) {
        ItemFormDto item = itemService.getItemDtl(itemId);
        model.addAttribute("item",item);
        return "reportShop/reportItem";
    }

    @PostMapping(value = "/item/{itemId}")
    public String reportItem(@PathVariable("itemId") Long itemId, HttpServletRequest request) {
        String reportContent = request.getParameter("reportContent");
        String reportCategory = request.getParameter("reportCategory");
        Item item = itemRepository.findById(itemId).orElse(null);

        ReportShopDto reportShopDto = new ReportShopDto();
        reportShopDto.setItem(item);
        reportShopDto.setTargetCreatedBy(item.getCreatedBy());
        reportShopDto.setReportCategory(reportCategory);
        reportShopDto.setReportContent(reportContent);
        reportShopService.report(reportShopDto);
        return "reportShop/reportSuccess";
    }

    @GetMapping(value = "/inquiry/{inquiryId}")
    public String openReportInquiry(@PathVariable Long inquiryId, Model model) {
        InquiryDto inquiry = inquiryService.getInquiryDtl(inquiryId);
        model.addAttribute("inquiry",inquiry);
        return "reportShop/reportInquiry";
    }

    @PostMapping(value = "/inquiry/{inquiryId}")
    public String reportInquiry(@PathVariable("inquiryId") Long inquiryId, HttpServletRequest request) {
        String reportContent = request.getParameter("reportContent");
        String reportCategory = request.getParameter("reportCategory");
        Inquiry inquiry = inquiryRepository.findById(inquiryId).orElse(null);

        ReportShopDto reportShopDto = new ReportShopDto();
        reportShopDto.setInquiry(inquiry);
        reportShopDto.setTargetCreatedBy(inquiry.getCreatedBy());
        reportShopDto.setReportCategory(reportCategory);
        reportShopDto.setReportContent(reportContent);
        reportShopService.report(reportShopDto);
        return "reportShop/reportSuccess";
    }

    @GetMapping(value = "/inquiryAnswer/{inquiryAnswerId}")
    public String openReportInquiryAnswer(@PathVariable Long inquiryAnswerId, Model model) {
        InquiryAnswerDto inquiryAnswer = inquiryAnswerService.getInquiryAnswerDtl(inquiryAnswerId);
        model.addAttribute("inquiryAnswer",inquiryAnswer);
        return "reportShop/reportInquiryAnswer";
    }

    @PostMapping(value = "/inquiryAnswer/{inquiryAnswerId}")
    public String reportInquiryAnswer(@PathVariable("inquiryAnswerId") Long inquiryAnswerId, HttpServletRequest request) {
        String reportContent = request.getParameter("reportContent");
        String reportCategory = request.getParameter("reportCategory");
        InquiryAnswer inquiryAnswer = inquiryAnswerRepository.findById(inquiryAnswerId).orElse(null);

        ReportShopDto reportShopDto = new ReportShopDto();
        reportShopDto.setInquiryAnswer(inquiryAnswer);
        reportShopDto.setTargetCreatedBy(inquiryAnswer.getCreatedBy());
        reportShopDto.setReportCategory(reportCategory);
        reportShopDto.setReportContent(reportContent);
        reportShopService.report(reportShopDto);
        return "reportShop/reportSuccess";
    }

    @GetMapping(value = "/review/{reviewId}")
    public String openReportReview(@PathVariable Long reviewId, Model model) {
        ReviewDto review = reviewService.getReviewDtl(reviewId);
        model.addAttribute("review", review);
        return "reportShop/reportReview";
    }

    @PostMapping(value = "/review/{reviewId}")
    public String reportReview(@PathVariable("reviewId") Long reviewId, HttpServletRequest request) {
        String reportContent = request.getParameter("reportContent");
        String reportCategory = request.getParameter("reportCategory");
        Review review = reviewRepository.findById(reviewId).orElse(null);

        ReportShopDto reportShopDto = new ReportShopDto();
        reportShopDto.setReview(review);
        reportShopDto.setTargetCreatedBy(review.getCreatedBy());
        reportShopDto.setReportCategory(reportCategory);
        reportShopDto.setReportContent(reportContent);
        reportShopService.report(reportShopDto);
        return "reportShop/reportSuccess";
    }
}
