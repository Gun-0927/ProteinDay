package com.blue.bluearchive.userpage.controller;

import com.blue.bluearchive.admin.dto.CategoryDto;
import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.admin.service.CategoryService;
import com.blue.bluearchive.board.dto.BoardSearchDto;
import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.member.dto.CustomUserDetails;
import com.blue.bluearchive.userpage.dto.SearchDto;
import com.blue.bluearchive.userpage.service.UserPageBoardService;
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
public class UserPageBoardController {
    private final UserPageBoardService userPageBoardService;
    private final CategoryService categoryService;


    public String getUserIdx(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Object principal = authentication.getPrincipal();
        CustomUserDetails userDetails = (CustomUserDetails) principal;
        String  userIdx=Long.toString(userDetails.getIdx());
        return userIdx;
    }


    @GetMapping(value = {"/user/boardRecord/all","/user/boardRecord/all/{page}"})
    public String boardWirteRecord(SearchDto searchDto,
                                   @PathVariable("page") Optional<Integer> page, Model model){
        String  userIdx=getUserIdx();
        //페이지 설정
        Pageable pageable = PageRequest.of(page.isPresent()?page.get():0,5);
        model.addAttribute("maxPage",5);

        //리스트 데이터들 페이지 객체화
        Page<Board> boardList = userPageBoardService.getBoardByCreatedBy(userIdx,searchDto,pageable);
        System.out.println("보드 컨트롤러 =============================="+boardList.getPageable()+"========="+boardList.getTotalPages());
        model.addAttribute("searchDto",searchDto);
        model.addAttribute("boardList", boardList);


        List<CategoryDto> categoryList = categoryService.getAllCategory();
        model.addAttribute("categoryList", categoryList);

        return "userpage/userBoardLog";
    }

    @GetMapping(value = {"/user/boardRecord/{categoryId}","/user/boardRecord/{categoryId}/{page}"})
    public String boardWirteRecordCategory(@PathVariable int categoryId,SearchDto searchDto,
                                   @PathVariable("page") Optional<Integer> page,Model model){
        Category category = categoryService.getCategoryById(categoryId);
        System.out.println("======================게시물 목록 컨트롤러 진입=================");
        String  userIdx=getUserIdx();
        //페이지 설정
        Pageable pageable = PageRequest.of(page.isPresent()?page.get():0,5);
        model.addAttribute("maxPage",5);

        //리스트 데이터들 페이지 객체화
        Page<Board> boardList = userPageBoardService.getBoardByCreatedBy(category,userIdx,searchDto,pageable);
        model.addAttribute("searchDto",searchDto);
        model.addAttribute("boardList", boardList);


        List<CategoryDto> categoryList = categoryService.getAllCategory();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("selectCategoryId", categoryId);

        return "userpage/userBoardLog";
    }



}
