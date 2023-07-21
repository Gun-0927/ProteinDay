package com.blue.bluearchive.report_shop.dto;

import com.blue.bluearchive.report_shop.entity.ReportShop;
import com.blue.bluearchive.shop.entity.Item;
import com.blue.bluearchive.shop.entity.Review;
import com.blue.bluearchive.shop_inquiry.entity.Inquiry;
import com.blue.bluearchive.shop_inquiry.entity.InquiryAnswer;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReportShopDto {
    @NotBlank(message = "내용은 필수 입니다")
    private String reportContent;
    @NotBlank(message = "카테고리 선택은 필수 입니다")
    private String reportCategory;
    private String targetCreatedBy;
    private Item item;
    private Inquiry inquiry;
    private InquiryAnswer inquiryAnswer;
    private Review review;

    public ReportShop createReportShop() {
        ReportShop reportShop = new ReportShop();
        reportShop.setReportContent(this.reportContent);
        reportShop.setReportCategory(this.reportCategory);
        reportShop.setItem(this.item);
        reportShop.setInquiry(this.inquiry);
        reportShop.setInquiryAnswer(this.inquiryAnswer);
        reportShop.setReview(this.review);
        reportShop.setTargetCreatedBy(this.targetCreatedBy);
        return reportShop;
    }
}
