package com.blue.bluearchive.userpage.controller;


import com.blue.bluearchive.admin.dto.CategoryDto;
import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.admin.service.CategoryService;
import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.member.dto.CustomUserDetails;
import com.blue.bluearchive.userpage.dto.SearchDto;
import com.blue.bluearchive.userpage.dto.UserPageCommentDto;
import com.blue.bluearchive.userpage.dto.UserPageCommentsCommentDto;
import com.blue.bluearchive.userpage.service.UserPageBoardService;
import com.blue.bluearchive.userpage.service.UserPageCommentService;
import com.blue.bluearchive.userpage.service.UserPageCommentsCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserPageCommentController {
    private final UserPageCommentService userPageCommentService;
    private final UserPageCommentsCommentService userPageCommentsCommentService;
    private final CategoryService categoryService;

    public String getUserIdx(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Object principal = authentication.getPrincipal();
        CustomUserDetails userDetails = (CustomUserDetails) principal;
        String  userIdx=Long.toString(userDetails.getIdx());
        return userIdx;
    }


    @GetMapping(value = {"/user/commentRecord/all","/user/commentRecord/all/{page}"})
    public String commentRecord(SearchDto searchDto,
                                @PathVariable("page") Optional<Integer> page, Model model){
        String  userIdx=getUserIdx();
        //페이지 설정
        Pageable pageable = PageRequest.of(page.isPresent()?page.get():0,5);
        model.addAttribute("maxPage",5);

        //리스트 데이터들 페이지 객체화
        Page<UserPageCommentDto> commentList = userPageCommentService.getCommentByCreatedBy(userIdx,searchDto,pageable);
//        System.out.println("보드 컨트롤러 =============================="+commentList.getPageable()+"========="+commentList.getTotalPages());
        model.addAttribute("searchDto",searchDto);
        model.addAttribute("commentList", commentList);


        List<CategoryDto> categoryList = categoryService.getAllCategory();
        model.addAttribute("categoryList", categoryList);

        return "userpage/userCommentLog";
    }

    @GetMapping(value = {"/user/commentRecord/{categoryId}","/user/commentRecord/{categoryId}/{page}"})
    public String commentRecordCategoryId(@PathVariable int categoryId,SearchDto searchDto,
                                          @PathVariable("page") Optional<Integer> page,Model model){
        Category category = categoryService.getCategoryById(categoryId);
        String  userIdx=getUserIdx();

        //페이지 설정
        Pageable pageable = PageRequest.of(page.isPresent()?page.get():0,5);
        model.addAttribute("maxPage",5);

        //리스트 데이터들 페이지 객체화
        Page<UserPageCommentDto> commentList = userPageCommentService.getCommentByCreatedBy(category,userIdx,searchDto,pageable);
        model.addAttribute("searchDto",searchDto);
        model.addAttribute("commentList", commentList);

        List<CategoryDto> categoryList = categoryService.getAllCategory();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("selectCategoryId", categoryId);

        return "userpage/userCommentLog";
    }
//    userPageCommentsCommentService.getCommentsCommentByCreatedBy


    @GetMapping(value = {"/user/commentsCommentRecord/all","/user/commentsCommentRecord/all/{page}"})
    public String commentsCommentRecord(SearchDto searchDto,
                                @PathVariable("page") Optional<Integer> page, Model model){
        String  userIdx=getUserIdx();
        //페이지 설정
        Pageable pageable = PageRequest.of(page.isPresent()?page.get():0,5);
        model.addAttribute("maxPage",5);

        //리스트 데이터들 페이지 객체화
        Page<UserPageCommentsCommentDto> commentsCommentList = userPageCommentsCommentService.getCommentsCommentByCreatedBy(userIdx,searchDto,pageable);
//        System.out.println("보드 컨트롤러 =============================="+commentsCommentList.getPageable()+"========="+commentsCommentList.getTotalPages());
        model.addAttribute("searchDto",searchDto);
        model.addAttribute("commentsCommentList", commentsCommentList);


        List<CategoryDto> categoryList = categoryService.getAllCategory();
        model.addAttribute("categoryList", categoryList);

        return "userpage/userCommentsCommentLog";
    }

    @GetMapping(value = {"/user/commentsCommentRecord/{categoryId}","/user/commentsCommentRecord/{categoryId}/{page}"})
    public String commentsCommentRecordCategoryId(@PathVariable int categoryId,SearchDto searchDto,
                                                  @PathVariable("page") Optional<Integer> page, Model model){
        Category category = categoryService.getCategoryById(categoryId);
        String  userIdx=getUserIdx();
        //페이지 설정
        Pageable pageable = PageRequest.of(page.isPresent()?page.get():0,5);
        model.addAttribute("maxPage",5);

        //리스트 데이터들 페이지 객체화
        Page<UserPageCommentsCommentDto> commentsCommentList = userPageCommentsCommentService.getCommentsCommentByCreatedBy(category,userIdx,searchDto,pageable);
//        System.out.println("보드 컨트롤러 =============================="+commentList.getPageable()+"========="+commentList.getTotalPages());
        model.addAttribute("searchDto",searchDto);
        model.addAttribute("commentsCommentList", commentsCommentList);


        List<CategoryDto> categoryList = categoryService.getAllCategory();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("selectCategoryId", categoryId);

        return "userpage/userCommentsCommentLog";
    }



//    @GetMapping("/user/commentsCommentRecord")
//    public String commentsCommentRecord(Model model){
//        String  userIdx=getUserIdx();
//        List<UserPageCommentsCommentDto> commentsCommentList = userPageCommentsCommentService.getCommentsCommentByCreatedBy(userIdx);
//        model.addAttribute("commentsCommentList", commentsCommentList);
//
//        List<CategoryDto> categoryList = categoryService.getAllCategory();
//        model.addAttribute("categoryList", categoryList);
//        return "userpage/userCommentsCommentLog";
//    }
//
//    @GetMapping(value = "/user/commentsCommentRecord/{categoryId}")
//    public String commentsCommentRecordCategoryId(@PathVariable int categoryId, Model model){
//        System.out.println("==============컨트롤러 진입===================");
//        String userIdx=getUserIdx();
//
//        List<UserPageCommentsCommentDto> commentsCommentList = userPageCommentsCommentService.getCommentsCommentByCreatedBy(userIdx,categoryId);
//        model.addAttribute("commentsCommentList", commentsCommentList);
//        List<CategoryDto> categoryList = categoryService.getAllCategory();
//        model.addAttribute("categoryList", categoryList);
//        model.addAttribute("selectCategoryId", categoryId);
//
//        return "userpage/userCommentLog";
//    }
}
