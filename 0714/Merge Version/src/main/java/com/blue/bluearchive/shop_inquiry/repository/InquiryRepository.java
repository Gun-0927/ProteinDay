package com.blue.bluearchive.shop_inquiry.repository;

import com.blue.bluearchive.shop_inquiry.dto.InquiryDto;
import com.blue.bluearchive.shop_inquiry.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    List<InquiryDto> findByItemId(Long itemId);
    
}
