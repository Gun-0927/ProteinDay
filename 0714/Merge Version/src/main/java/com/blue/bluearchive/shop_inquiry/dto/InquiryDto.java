package com.blue.bluearchive.shop_inquiry.dto;

import com.blue.bluearchive.shop_inquiry.entity.Inquiry;
import com.blue.bluearchive.shop_inquiry.entity.InquiryAnswer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class InquiryDto {
    private Long id;
    private Long itemId;
    private String content;
    private String createdBy;
    private LocalDateTime regTime;
    private List<InquiryAnswer> inquiryAnswerList;

    public InquiryDto(Long id, Long itemId, String content, String createdBy, LocalDateTime regTime) {
        this.id = id;
        this.itemId = itemId;
        this.content = content;
        this.createdBy = createdBy;
        this.regTime = regTime;
    }

    // 엔티티로부터 DTO 생성
    public static InquiryDto fromEntity(Inquiry inquiry) {
        return new InquiryDto(inquiry.getId(), inquiry.getItem().getId(), inquiry.getContent(), inquiry.getCreatedBy(), inquiry.getRegTime());
    }
}
