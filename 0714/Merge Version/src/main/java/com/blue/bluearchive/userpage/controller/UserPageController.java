package com.blue.bluearchive.userpage.controller;


import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.member.dto.CustomUserDetails;
import com.blue.bluearchive.userpage.dto.UserGraphFormDto;
import com.blue.bluearchive.userpage.dto.UserMainCategoryDto;
import com.blue.bluearchive.userpage.dto.UserMainDto;
import com.blue.bluearchive.userpage.entity.UserCategoryLog;
import com.blue.bluearchive.userpage.repository.UserCategoryLogRepository;
import com.blue.bluearchive.userpage.service.UserCategoryLogService;
import com.blue.bluearchive.userpage.service.UserGraphLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserPageController {
    private final UserGraphLogService userGraphLogService;
    private final UserCategoryLogService userCategoryLogService;
    public String getUserIdx(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Object principal = authentication.getPrincipal();
        CustomUserDetails userDetails = (CustomUserDetails) principal;
        String  userIdx=Long.toString(userDetails.getIdx());
        return userIdx;
    }


    @GetMapping("/user/login")
    public String login(){
        return "login";
    }

    @GetMapping("/user/mypage")
    public String myPage(Model model){
        long userIdx= Long.valueOf(getUserIdx());

        List<UserMainCategoryDto> userMainCategoryDtoList = userCategoryLogService.getCategoryLogData(userIdx);
        model.addAttribute("userMainCategoryDtoList",userMainCategoryDtoList);

        List<UserMainDto> userMainDtos = userGraphLogService.getUserGraphLog(userIdx);
        log.info("========메인컨트롤러 진입=========");
        log.info(userMainDtos.toString());
        model.addAttribute("userMainDtos",userMainDtos);
        return "userPage/user";
    }


    @GetMapping("/user/grapInformation")
    public String grapInformation(){
        userCategoryLogService.logAllUserCategoryCount();
        return "userPage/userChange";
    }

    @PostMapping("/user/grapInformation")
    public String grapInformationLog(@Valid UserGraphFormDto userGraphFormDto, BindingResult bindingResult, Model model){
        long userIdx= Long.valueOf(getUserIdx());
        log.info("========컨트롤러 진입=========");
        log.info(userGraphFormDto.toString());
//        userGraphLogService.testSaveUserGraphLogs(userIdx);//그래프 확인용 임의 데이터 20개 넣는 메서드
        //원래 동작에 필요한 메서드
        userGraphLogService.saveUserGrahpLog(userGraphFormDto,userIdx);

        return "redirect:/user/mypage";
    }



}
