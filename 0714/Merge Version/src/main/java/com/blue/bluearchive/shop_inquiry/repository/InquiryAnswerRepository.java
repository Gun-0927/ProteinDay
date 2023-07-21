package com.blue.bluearchive.shop_inquiry.repository;

import com.blue.bluearchive.shop_inquiry.dto.InquiryAnswerDto;
import com.blue.bluearchive.shop_inquiry.entity.InquiryAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryAnswerRepository extends JpaRepository<InquiryAnswer, Long> {
    List<InquiryAnswerDto> findByInquiryId(Long inquiryId);
}
