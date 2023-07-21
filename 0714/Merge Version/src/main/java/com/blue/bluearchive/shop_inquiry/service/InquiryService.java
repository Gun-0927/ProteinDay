package com.blue.bluearchive.shop_inquiry.service;

import com.blue.bluearchive.member.entity.Member;
import com.blue.bluearchive.member.repository.MemberRepository;
import com.blue.bluearchive.shop.entity.Item;
import com.blue.bluearchive.shop.repository.ItemRepository;
import com.blue.bluearchive.shop_inquiry.dto.InquiryAnswerDto;
import com.blue.bluearchive.shop_inquiry.dto.InquiryDto;
import com.blue.bluearchive.shop_inquiry.entity.Inquiry;
import com.blue.bluearchive.shop_inquiry.entity.InquiryAnswer;
import com.blue.bluearchive.shop_inquiry.repository.InquiryAnswerRepository;
import com.blue.bluearchive.shop_inquiry.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryService {

    @Autowired
    private final InquiryRepository inquiryRepository;

    @Autowired
    private final ItemRepository itemRepository;

    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final InquiryAnswerRepository inquiryAnswerRepository;


    @Transactional(readOnly = true)
    public List<Inquiry> getInquiryListByItemId(Long itemId) {
        List<Inquiry> inquiryList = new ArrayList<>();
        List<InquiryDto> inquiryDtoList = inquiryRepository.findByItemId(itemId);

        for (InquiryDto inquiryDto : inquiryDtoList) {
            Inquiry inquiry = new Inquiry();
            inquiry.setId(inquiryDto.getId());
            inquiryAnswerRepository.findByInquiryId(inquiryDto.getId());

            inquiry.setContent(inquiryDto.getContent());
            Member member = memberRepository.findByIdx(Long.valueOf(inquiryDto.getCreatedBy()));
            inquiry.setCreatedBy(member.getNickName());
            inquiry.setRegTime(inquiryDto.getRegTime());
            Item item = itemRepository.findById(inquiryDto.getItemId())
                    .orElseThrow(() -> new EntityNotFoundException("Item not found"));
            inquiry.setItem(item);

            List<InquiryAnswerDto> inquiryAnswerDtoList = inquiryAnswerRepository.findByInquiryId(inquiryDto.getId());
            List<InquiryAnswer> inquiryAnswerList = new ArrayList<>();

            for (InquiryAnswerDto inquiryAnswerDto : inquiryAnswerDtoList) {
                InquiryAnswer inquiryAnswer = new InquiryAnswer();
                inquiryAnswer.setId(inquiryAnswerDto.getId());
                inquiryAnswer.setContent(inquiryAnswerDto.getContent());
                inquiryAnswer.setRegTime(inquiryAnswerDto.getRegTime());

                inquiryAnswerList.add(inquiryAnswer);
            }

            inquiry.setInquiryAnswer(inquiryAnswerList);

            inquiryList.add(inquiry);
        }

        return inquiryList;
    }


    @Transactional(readOnly = false)
    public void inquiryWrite(Long itemId, String email, String content) {
        // Item 조회
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 아이템 ID입니다."));

        // Member 조회
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new IllegalArgumentException("유효하지 않은 회원 이메일입니다.");
        }

        // Inquiry 생성 및 저장
        Inquiry inquiry = new Inquiry();

        inquiry.setItem(item);
        if (content != null) {
            inquiry.setContent(content);
        } else {
            throw new IllegalArgumentException("문의 내용을 입력해주세요.");
        }

        inquiryRepository.save(inquiry);
    }


    @Transactional(readOnly = false)
    public void inquiryDelete(Long id) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 문의 ID입니다."));
        inquiryRepository.delete(inquiry);
    }

    @Transactional(readOnly = false)
    public void inquiryUpdate(Long id, String content) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 문의 ID입니다."));
        inquiry.setContent(content);
    }

    @Transactional(readOnly = true)
    public InquiryDto getInquiryDtl(Long id) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 문의ID입니다."));
        InquiryDto inquiryDto = InquiryDto.fromEntity(inquiry);
        Member member = memberRepository.findByIdx(Long.valueOf(inquiry.getCreatedBy()));
        inquiryDto.setCreatedBy(member.getNickName());
        return inquiryDto;
    }

}
