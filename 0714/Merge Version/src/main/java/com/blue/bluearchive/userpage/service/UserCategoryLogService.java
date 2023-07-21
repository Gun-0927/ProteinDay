package com.blue.bluearchive.userpage.service;

import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.admin.repository.CategoryRepository;
import com.blue.bluearchive.member.dto.CustomUserDetails;
import com.blue.bluearchive.member.entity.Member;
import com.blue.bluearchive.member.repository.MemberRepository;
import com.blue.bluearchive.userpage.dto.UserMainCategoryDto;
import com.blue.bluearchive.userpage.dto.UserMainLogDataDto;
import com.blue.bluearchive.userpage.entity.UserCategoryCount;
import com.blue.bluearchive.userpage.entity.UserCategoryLog;
import com.blue.bluearchive.userpage.repository.UserCategoryCountRepository;
import com.blue.bluearchive.userpage.repository.UserCategoryLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCategoryLogService {
    private final UserCategoryCountRepository userCategoryCountRepository;
    private final UserCategoryLogRepository userCategoryLogRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;



    //해당하는 카테고리 유저 부분 조회수 증가 기록
    @Transactional(readOnly = false)
    public void categoryCount(Category category){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            try {
                // 인증된 사용자인지 확인하고, CustomUserDetails로 형변환하여 처리
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
                String loginMemberIdx = Long.toString(userDetails.getIdx());
                Member member = memberRepository.findByIdx(userDetails.getIdx());
                UserCategoryCount userCategoryCount =
                        userCategoryCountRepository.findByMemberAndCategory(member, category);
                userCategoryCount.setCategoryAllCount(userCategoryCount.getCategoryAllCount() + 1);
                // 여기에 로그인된 사용자에 대한 처리를 추가하세요.
            } catch (ClassCastException e) {
                // 예외 처리: 인증된 사용자의 principal이 CustomUserDetails 타입이 아닌 경우
                e.printStackTrace();
            } catch (NullPointerException e) {
                // 예외 처리: 인증되지 않은 사용자
                e.printStackTrace();
            }
        }

    }


    @Transactional(readOnly = false)
    public void newMemberCategoryLog(Member newMember){ //회원가입시 회원수만큼 엔티티 추가
        System.out.println("============카테고리 리스트 제작 부분 진입 ==================");
        List<Category> categories = categoryRepository.findAll();
// UserCategoryCount 생성 및 추가
        List<UserCategoryCount> userCategoryCounts = new ArrayList<>();
        for (Category category : categories) {
            UserCategoryCount userCategoryCount = new UserCategoryCount();
            userCategoryCount.setMember(newMember);
            userCategoryCount.setCategory(category);
            userCategoryCount.setCategoryPreCount(0);
            userCategoryCount.setCategoryAllCount(0);
            userCategoryCounts.add(userCategoryCount);
        }
        newMember.setUserCategoryCount(userCategoryCounts); // Member와 UserCategoryCount 연관 설정
    }
    @Transactional(readOnly = false)
    public void newCategoryCategoryLog(Category newCategory){ //카테고리생성시 회원수만큼 엔티티 추가
        List<Member> members = memberRepository.findAll();
        List<UserCategoryCount> userCategoryCounts = new ArrayList<>();
        for (Member member : members) {
            UserCategoryCount userCategoryCount = new UserCategoryCount();
            userCategoryCount.setMember(member);
            userCategoryCount.setCategory(newCategory);
            userCategoryCount.setCategoryPreCount(0);
            userCategoryCount.setCategoryAllCount(0);
            userCategoryCounts.add(userCategoryCount);
        }
        newCategory.setUserCategoryCount(userCategoryCounts); // Member와 UserCategoryCount 연관 설정
    }


    @Transactional(readOnly = false)
    public void logAllUserCategoryCount(){ //특정 시간이 되면 log날짜를 기록하는 서비스
        List<UserCategoryCount> userCategoryCounts = userCategoryCountRepository.findAll();
        List<UserCategoryLog> userCategoryLogs = new ArrayList<>();

        for (UserCategoryCount userCategoryCount : userCategoryCounts) {
            UserCategoryLog userCategoryLog = new UserCategoryLog();
            userCategoryLog.setMember(userCategoryCount.getMember());
            userCategoryLog.setCategory(userCategoryCount.getCategory());

            int categoryCount = userCategoryCount.getCategoryAllCount() - userCategoryCount.getCategoryPreCount();
            userCategoryLog.setCategoryCount(categoryCount);

            userCategoryCount.setCategoryPreCount(userCategoryCount.getCategoryAllCount());

            userCategoryLogs.add(userCategoryLog);
        }
        userCategoryCountRepository.saveAll(userCategoryCounts);
        userCategoryLogRepository.saveAll(userCategoryLogs);
    }


    @Transactional(readOnly = true)
    public List<UserMainCategoryDto> getCategoryLogData(long userIdx){
        Member member = memberRepository.findByIdx(userIdx);
        List<UserMainCategoryDto> allDataDto = new ArrayList<>();
        List<UserCategoryLog> entityDataList = userCategoryLogRepository.findByMember(member);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");

        for(UserCategoryLog log : entityDataList){
            UserMainCategoryDto currentDto = null;
            for(UserMainCategoryDto dto : allDataDto) {
                if(dto.getCategoryName().equals(log.getCategory().getCategoryName())){
                    currentDto = dto;
                    break;
                }
            }

            if(currentDto == null) {
                currentDto = new UserMainCategoryDto();
                currentDto.setCategoryName(log.getCategory().getCategoryName());
                currentDto.setUserMainLogDataDtoList(new ArrayList<>());
                allDataDto.add(currentDto);
            }

            UserMainLogDataDto logDataDto = new UserMainLogDataDto();
            logDataDto.setCategoryCount(log.getCategoryCount());
            logDataDto.setLogTime(log.getLogTime().format(formatter));

            currentDto.getUserMainLogDataDtoList().add(logDataDto);
        }
        System.out.println("============================서비스부분==================================");
        System.out.println(allDataDto);

        return allDataDto;
    }










}
