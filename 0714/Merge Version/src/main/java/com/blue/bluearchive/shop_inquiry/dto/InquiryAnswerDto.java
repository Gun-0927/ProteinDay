package com.blue.bluearchive.shop_inquiry.dto;

import com.blue.bluearchive.shop_inquiry.entity.InquiryAnswer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InquiryAnswerDto {
    private Long id;
    private Long inquiryId;
    private String content;
    private String createdBy;
    private LocalDateTime regTime;

    public InquiryAnswerDto(Long id, Long inquiryId, String content, String createdBy, LocalDateTime regTime) {
        this.id = id;
        this.inquiryId = inquiryId;
        this.content = content;
        this.createdBy = createdBy;
        this.regTime = regTime;
    }

    // 엔티티로부터 DTO 생성
    public static InquiryAnswerDto fromEntity(InquiryAnswer inquiryAnswer) {
        return new InquiryAnswerDto(inquiryAnswer.getId(), inquiryAnswer.getInquiry().getId(), inquiryAnswer.getContent(), inquiryAnswer.getCreatedBy(), inquiryAnswer.getRegTime());
    }
}
