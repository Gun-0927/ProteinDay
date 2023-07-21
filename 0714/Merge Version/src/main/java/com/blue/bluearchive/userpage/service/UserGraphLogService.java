package com.blue.bluearchive.userpage.service;

import com.blue.bluearchive.board.dto.CommentDto;
import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.member.entity.Member;
import com.blue.bluearchive.member.repository.MemberRepository;
import com.blue.bluearchive.userpage.dto.UserGraphFormDto;
import com.blue.bluearchive.userpage.dto.UserGraphTestDto;
import com.blue.bluearchive.userpage.dto.UserMainDto;
import com.blue.bluearchive.userpage.entity.UserGraphLog;
import com.blue.bluearchive.userpage.repository.UserGraphLogRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserGraphLogService {
    private final MemberRepository memberRepository;
    private final UserGraphLogRepository userGraphLogRepository;
    private final ModelMapper modelMapper;


    //그래프 확인을 위해 테스트용으로 데이터 저장
    @Transactional(readOnly = false)
    public void testSaveUserGraphLogs(long userIdx) {
        Member member = memberRepository.findByIdx(userIdx);
        UserGraphTestDto testDto = new UserGraphTestDto();
        LocalDateTime today = LocalDateTime.now();
        // 테스트 데이터 20개 생성
        for (int i = 0; i < 20; i++) {
            testDto.setLogTime(today.plusDays(i));  // 날짜를 하루씩 증가
            testDto.setUserGraphWeight(testDto.getUserGraphWeight() + 1);  // 값들을 1씩 증가
            testDto.setUserGraphBmi(testDto.getUserGraphBmi() + 1);
            testDto.setUserGraphIntakeCal(testDto.getUserGraphIntakeCal() + 1);
            testDto.setUserGraphSubtractCal(testDto.getUserGraphSubtractCal() + 1);

            UserGraphLog userGraphLog = modelMapper.map(testDto, UserGraphLog.class);
            userGraphLog.setMember(member);
            userGraphLogRepository.save(userGraphLog);
        }
    }

    @Transactional(readOnly = true)
    public List<UserMainDto> getUserGraphLog(long userIdx){
        Member member = memberRepository.findByIdx(userIdx);
        List<UserGraphLog> userGraphLogs = userGraphLogRepository.findAllByMember(member);
        List<UserMainDto> userMainDtos = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        for (UserGraphLog userGraphLog : userGraphLogs) {
            UserMainDto userMainDto = modelMapper.map(userGraphLog, UserMainDto.class);
            userMainDto.setLogTime(userGraphLog.getLogTime().format(formatter));
            userMainDtos.add(userMainDto);
        }
        return userMainDtos;
    }



    @Transactional(readOnly = false)
    public UserGraphLog saveUserGrahpLog(UserGraphFormDto userGraphFormDto, long userIdx){
        Member member = memberRepository.findByIdx(userIdx);

        // 해당 멤버의 가장 최신 log를 가져옴
        UserGraphLog lastLog = userGraphLogRepository.findTopByMemberOrderByLogTimeDesc(member);

        // 가장 최신 로그가 오늘 날짜인지 확인
        if (lastLog != null && lastLog.getLogTime().toLocalDate().isEqual(LocalDate.now())) {
            // 오늘 날짜이면 기존 로그를 업데이트
            modelMapper.map(userGraphFormDto, lastLog);
            return userGraphLogRepository.save(lastLog);
        } else {
            // 아니면 새로운 로그를 생성
            UserGraphLog newUserGraphLog = modelMapper.map(userGraphFormDto, UserGraphLog.class);
            newUserGraphLog.setMember(member);
            return userGraphLogRepository.save(newUserGraphLog);
        }
    }

}
