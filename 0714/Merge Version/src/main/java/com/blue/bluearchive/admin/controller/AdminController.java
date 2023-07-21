package com.blue.bluearchive.admin.controller;

import com.blue.bluearchive.admin.dto.*;
import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.admin.service.*;
import com.blue.bluearchive.board.service.BoardService;
import com.blue.bluearchive.board.service.CommentService;
import com.blue.bluearchive.board.service.CommentsCommentService;
import com.blue.bluearchive.constant.MemberStat;
import com.blue.bluearchive.member.entity.Member;
import com.blue.bluearchive.member.service.MemberService;
import com.blue.bluearchive.report.service.ReportService;
import com.blue.bluearchive.shop.entity.Item;
import com.blue.bluearchive.shop.entity.OrderItem;
import com.blue.bluearchive.shop.repository.OrderItemRepository;
import com.blue.bluearchive.shop.service.ItemService;
import com.blue.bluearchive.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    private final CategoryService categoryService;
    private final MemberService memberService;
    private final BoardService boardService;
    private final ItemService itemService;
    private final OrderService orderService;
    private final AdminReportService adminReportService;
    private final AdminBoardService adminBoardService;
    private final AdminCommentService adminCommentService;
    private final AdminCommentsCommentService adminCommentsCommentService;
    private final AdminCommunityCareService adminCommunityCareService;
    private final AdminUserCareSerivce userCareSerivce;


    @GetMapping("/main")
    public String adminMain(Model model) {
        List<CategoryDailyData> categoryDailyDataList = adminCommunityCareService.getCategoryDailyData();
        model.addAttribute("categoryDailyDataList", categoryDailyDataList);
        return "adminPage/manageMain";
    }

    @GetMapping("/userMgt/{page}")
    public String userMgt(@RequestParam String userState, Model model,AdminSearchDto adminSearchDto,  @PathVariable("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.get(),5);
        Page<AdminUserCareDto> userCareDtos = userCareSerivce.getMemberBoardPageMain(userState,adminSearchDto,pageable);
        System.out.println("검색했을떄===="+userCareDtos);
        model.addAttribute("maxPage",10);
        model.addAttribute("userCareDtos", userCareDtos);
        model.addAttribute("searchDto", adminSearchDto);
        model.addAttribute("memberStatEnum", MemberStat.values());
        model.addAttribute("id", userState);
        return "adminPage/manageUser";
    }

    @GetMapping("/shopSellerWaitingMgt/{page}")
    public String shopSellerWaitingMgt(Model model,@PathVariable("page") Optional<Integer> page) {

        Pageable pageable = PageRequest.of(page.get(),5);
        Page<Member> member = userCareSerivce.getMemberShopMain(pageable);
        model.addAttribute("maxPage",5);
        model.addAttribute("member", member);
        return "shopAdmin/manageShop";
    }

    @GetMapping("/shopUserMgt/{page}")
    public String shopUserMgt(@RequestParam(required = false, defaultValue = "CONSUMER")String shopState,
                              AdminSearchDto adminSearchDto,Model model,
                              @PathVariable("page") Optional<Integer> page) {


        Pageable pageable = PageRequest.of(page.get(),5);
        Page<Member> member = userCareSerivce.getMemberShopUserMain(shopState, adminSearchDto, pageable);
        ArrayList<Integer> itemsNum = new ArrayList<>();
        ArrayList<Integer> buyItemsNum = new ArrayList<>();


        for(Member memberItem : member){
            Integer itemNum = itemService.getItemNum(String.valueOf(memberItem.getIdx()));
            Integer byItemNum = orderService.getBuyItemNum(String.valueOf(memberItem.getIdx()));

            itemsNum.add(itemNum != null ? itemNum : 0);
            buyItemsNum.add(byItemNum != null ? byItemNum : 0);
        }
        model.addAttribute("maxPage",5);
        model.addAttribute("member", member);
        model.addAttribute("searchDto", adminSearchDto);
        model.addAttribute("itemsNum", itemsNum);
        model.addAttribute("buyItemsNum", buyItemsNum);
        model.addAttribute("shopState", shopState);

        return "shopAdmin/manageUserShop";
    }

    @GetMapping("/sellUserMgt")
    public String sellUserMgt() {
        return "/adminPage/manageUserSell";
    }

    @GetMapping("/boardMgt/{page}")
    public String getBoardMgt(@RequestParam int id, Model model, AdminSearchDto adminSearchDto,
                              @PathVariable("page") Optional<Integer> page) {

        List<CategoryDto> categoryDtoList = categoryService.getAllCategory();
        model.addAttribute("categoryList", categoryDtoList);
        //페이지 설정
        Pageable pageable = PageRequest.of(page.get(),5);
        model.addAttribute("maxPage",5);
        //리스트 데이터들 페이지 객체화
        Page<AdminBoardDto> boardDtoList =  adminBoardService.getBoardByCreatedBy(id, adminSearchDto, pageable);
        model.addAttribute("boardDtoList", boardDtoList);
        model.addAttribute("searchDto", adminSearchDto);
        model.addAttribute("id", id);
        return "/adminPage/manageCommunity_board";  // Return as JSON
    }
    @GetMapping("/commentMgt/{page}")
    public String getBoardCommentMgt(@RequestParam int id, Model model, AdminSearchDto adminSearchDto,
                                     @PathVariable("page")Optional<Integer> page) {

        List<CategoryDto> categoryDtoList = categoryService.getAllCategory();
        model.addAttribute("categoryList", categoryDtoList);
        //페이지 설정
        Pageable pageable = PageRequest.of(page.get(),5);
        model.addAttribute("maxPage",5);
        //리스트 데이터들 페이지 객체화
        Page<AdminCommentDto> commentDtos =  adminCommentService.getCommentByCreatedBy(id, adminSearchDto, pageable);
        model.addAttribute("commentDtos", commentDtos);
        model.addAttribute("searchDto", adminSearchDto);
        model.addAttribute("id", id);
        return "adminPage/manageCommunity_comment";  // Return as JSON
    }
    @GetMapping("/commentsCommentMgt/{page}")
    public String getCommentsCommentMgt(@RequestParam int id,AdminSearchDto searchDto,Model model
            ,  @PathVariable("page")Optional<Integer> page) {
        List<CategoryDto> categoryDtoList = categoryService.getAllCategory();
        model.addAttribute("categoryList", categoryDtoList);
        System.out.println("id 아이디 아이디 아이디 "+ id);
        //페이지 설정
        Pageable pageable = PageRequest.of(page.get(),5);
        model.addAttribute("maxPage",5);
        //리스트 데이터들 페이지 객체화
        Page<AdminCommentsCommentDto> commentsCommentDtos =  adminCommentsCommentService.getCommentByCreatedBy(id, searchDto, pageable);
        model.addAttribute("commentsCommentDtos", commentsCommentDtos);
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("id", id);
        return "adminPage/manageCommunity_commentsComment";  // Return as JSON
    }

    @GetMapping("/newcategory")
    public String newCate() {
        return "adminPage/newCategory";
    }

    @GetMapping("/commMgt")
    public String commMgt(Model model) {
        List<CategoryDto> categoryStatisticsList = categoryService.getTotal();
        model.addAttribute("categoryStatisticsList", categoryStatisticsList);
        return "adminPage/manageCommunity";
    }

    @PostMapping("/createCate")
    public String newCate(@ModelAttribute CategoryDto categoryDto) throws Exception {
        try {
            categoryService.save(categoryDto);
            return "redirect:/admin/commMgt";

        } catch (IllegalArgumentException e) {
            return "redirect:/admin/newcategory?duplicateError&categoryName=" + URLEncoder.encode(categoryDto.getCategoryName(), "UTF-8");
        }
    }

    @GetMapping("delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable int id) {
        try {
            categoryService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/update/{id}")
    public String categoryUpdate(@PathVariable int id, Model model) {
        Category categoryDto = categoryService.getCategoryById(id);
        model.addAttribute("categoryDto", categoryDto);
        return "adminPage/categoryUpdate";
    }

    @PostMapping("/updateCategory")
    public String categoryUpdate(@ModelAttribute CategoryDto categoryDto) throws Exception {
        try {
            categoryService.update(categoryDto);
            return "redirect:/admin/commMgt";
        } catch (IllegalArgumentException e) {
            return "redirect:/admin/update/" + categoryDto.getCategoryId()
                    + "?duplicateError&categoryName=" + URLEncoder.encode(categoryDto.getCategoryName(), "UTF-8");
        }
    }

    @PostMapping("/deleteBoard")
    public ResponseEntity<?> deleteBoard(@RequestBody List<Integer> boardIds) {
        try {
            adminBoardService.deleteBoards(boardIds);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/adminUpdate/{boardId}")
    public String boardCategoryUpdate(@PathVariable int boardId,Model model){
        List<CategoryDto> categoryDtoList = categoryService.getAllCategory();
        String categoryName = boardService.getBoardById(boardId).getCategory().getCategoryName();
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("boardId", boardId);
        model.addAttribute("categoryList", categoryDtoList);
        return "adminPage/adminUpdateCategory";
    }
    @Transactional
    @GetMapping("/adminUpdate2/{updateCategory}/{boardId}")
    public String changeBoardCategoryName(@PathVariable int updateCategory, @PathVariable int boardId) {
        adminBoardService.updateCategoryNameById(boardId, updateCategory);

        return "redirect:/admin/boardMgt/0?id=0"; // 원하는 리다이렉션 경로로 변경해주세요.
    }

    //대댓글 삭제 아작스
    @PostMapping("/deleteCommentsComment")
    public ResponseEntity<?> deleteCommentsComment(@RequestBody List<Integer> commentsCommentIds) {
        try {
            adminCommentsCommentService.deletes(commentsCommentIds);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //댓글 삭제
    @PostMapping("/deleteComment")
    public ResponseEntity<?> deleteComments(@RequestBody List<Integer> commentsIds) {
        try {
            System.out.println("아이디아이디"+commentsIds);
            adminCommentService.deletes(commentsIds);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //    게시글 신고 클릭시 팝업
    @GetMapping("/boardReport/{boardId}")
    public String boardReportResult(@PathVariable int boardId,@RequestParam(defaultValue = "1") int page, Model model){
        int pageSize = 5; // 한 페이지에 보여줄 게시글 수
        ReportPageDto reportPageDto = adminReportService.getReportsForBoard(boardId, page, pageSize);
        List<ReportDto> reportDtos = adminReportService.getReportsForBoard(boardId);
        model.addAttribute("reportPageDto", reportPageDto);
        model.addAttribute("reportDtos", reportDtos);
        model.addAttribute("boardId", boardId);

        return "adminPage/boardReportResult";
    }

    //댓글 신고 클릭시 팝업
    @GetMapping("/commentReport/{commentId}")
    public String commentReportResult(@PathVariable int commentId, @RequestParam(defaultValue = "1") int page, Model model){
        int pageSize = 5; // 한 페이지에 보여줄 게시글 수
        ReportPageDto reportPageDto = adminReportService.getReportsForComment(commentId, page, pageSize);
        List<ReportDto> reportDtos = adminReportService.getReportsForComment(commentId);
        model.addAttribute("commentId", commentId);
        model.addAttribute("reportDtos", reportDtos);
        model.addAttribute("reportPageDto", reportPageDto);

        return "adminPage/commentReportResult";
    }
    @GetMapping("/commentsComment/{commentsCommentId}")
    public String commentsCommentReportResult(@PathVariable int commentsCommentId, @RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 5; // 한 페이지에 보여줄 게시글 수
        ReportPageDto reportPageDto = adminReportService.getReportsForCommentsComment(commentsCommentId, page, pageSize);
        List<ReportDto> reportDtos = adminReportService.getReportsForCommentsComment(commentsCommentId);
        model.addAttribute("commentsCommentId", commentsCommentId);
        model.addAttribute("reportPageDto", reportPageDto);
        model.addAttribute("reportDtos", reportDtos);

        return "adminPage/commentsCommentReportResult";
    }
    @PostMapping("/confirmReports")
    public ResponseEntity<?> confirmReports(@RequestBody List<Integer> reportIds) {
        try {
            int handledReportCount = adminReportService.confirmReports(reportIds);
            return ResponseEntity.ok(handledReportCount);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/userReportList/{id}")
    public String userUntreatedReportList(@PathVariable Long id, Model model, @RequestParam(defaultValue = "1") int page) {
        System.out.println("id====" + id);
        int pageSize = 5; // 한 페이지에 보여줄 게시글 수
        ReportPageDto reportPageDto = adminReportService.getNoReportResult(id, page, pageSize);
        List<ReportDto> reportDtos = adminReportService.okReportResult(id);
        model.addAttribute("userId", id);
        model.addAttribute("reportPageDto", reportPageDto);
        model.addAttribute("reportDtos", reportDtos);
        return "adminPage/untreatedReportList";
    }
//    @GetMapping("/boardMgt/{page}")
//    public String getBoardMgt(@RequestParam int id, Model model, AdminSearchDto adminSearchDto,
//                              @PathVariable("page") Optional<Integer> page) {
//
//        List<CategoryDto> categoryDtoList = categoryService.getAllCategory();
//        model.addAttribute("categoryList", categoryDtoList);
//        //페이지 설정
//        Pageable pageable = PageRequest.of(page.get(),5);
//        model.addAttribute("maxPage",5);
//        //리스트 데이터들 페이지 객체화
//        Page<AdminBoardDto> boardDtoList =  adminBoardService.getBoardByCreatedBy(id, adminSearchDto, pageable);
//        model.addAttribute("boardDtoList", boardDtoList);
//        model.addAttribute("searchDto", adminSearchDto);
//        model.addAttribute("id", id);
//        return "/adminPage/manageCommunity_board";  // Return as JSON
//    }
    @GetMapping("/userRecord/{page}")
    public String userActivityBoardRecord(@RequestParam Long selectUser, Model model, @PathVariable("page") Optional<Integer> page){
        String name = userCareSerivce.findUser(selectUser);
        Pageable pageable = PageRequest.of(page.get(),5);
        model.addAttribute("maxPage",5);
        Page<AdminCareMemberBoard> memberBoards = userCareSerivce.getMemberBoardPageMain(selectUser,pageable);
        model.addAttribute("memberBoard",memberBoards);
        model.addAttribute("name", name);
        model.addAttribute("selectUser", selectUser);
        return "adminPage/userActivityBoardRecord";
    }

    @GetMapping("/userCommentRecord/{page}")
    public String userActivityCommentRecord(@RequestParam Long selectUser, Model model, @PathVariable("page") Optional<Integer> page){
        String name = userCareSerivce.findUser(selectUser);
        Pageable pageable = PageRequest.of(page.get(),5);
        model.addAttribute("maxPage",5);
        Page<AdminMemberCommentDto> membercomments = userCareSerivce.getMemberCommentPageMain(selectUser,pageable);
        model.addAttribute("comments",membercomments);
        model.addAttribute("name", name);
        model.addAttribute("selectUser", selectUser);
        return "adminPage/userActivityCommentRecord";
    }
    @GetMapping("/userCommentsCommentRecord/{page}")
    public String userActivityCommentsCommentRecord(@RequestParam Long selectUser, Model model, @PathVariable("page") Optional<Integer> page){
        String name = userCareSerivce.findUser(selectUser);
        Pageable pageable = PageRequest.of(page.get(),5);
        model.addAttribute("maxPage",5);
        Page<AdminMemberCommentsCommentDto> memberCommentsComment = userCareSerivce.getMemberCommentsCommentPageMain(selectUser,pageable);
        model.addAttribute("memberCommentsComment",memberCommentsComment);
        model.addAttribute("name", name);
        model.addAttribute("selectUser", selectUser);
        return "adminPage/userActivityCommentsCommentRecord";
    }

    @GetMapping("/userShopBuyRecord/{page}")
    public String userShopBuyRecord(@RequestParam Long selectUser, Model model, @PathVariable("page") Optional<Integer> page){
        Pageable pageable = PageRequest.of(page.get(),5);
        model.addAttribute("maxPage",5);

        Member member = memberService.findMember(selectUser);

        Page<OrderItem> memberBuyItem = itemService.getItemBuyerHistPage(String.valueOf(selectUser),pageable);
        ArrayList<String> itemNm = new ArrayList<>();
        ArrayList<String> sellerNm = new ArrayList<>();

        for(OrderItem item : memberBuyItem){
            itemNm.add(item.getItem().getItemNm());
            Member seller = memberService.findMember(Long.valueOf(item.getItem().getCreatedBy()));
            sellerNm.add(seller.getNickName());

        }
        model.addAttribute("itemNm", itemNm);
        model.addAttribute("sellerNm", sellerNm);
        model.addAttribute("memberBuyItem",memberBuyItem);
        model.addAttribute("name", member.getNickName());
        model.addAttribute("selectUser", selectUser);
        return "shopAdmin/userBuyShopRecord";
    }

    @GetMapping("/userShopSellRecord/{page}")
    public String userShopSellRecord(@RequestParam Long selectUser, Model model, @PathVariable("page") Optional<Integer> page){
        Pageable pageable = PageRequest.of(page.get(),5);
        model.addAttribute("maxPage",5);

        Member member = memberService.findMember(selectUser);
        Page<OrderItem> memberSellItem = itemService.getItemSellerHistPage(String.valueOf(selectUser),pageable);
        ArrayList<String> itemNm = new ArrayList<>();
        ArrayList<String> buyerNm = new ArrayList<>();

        for(OrderItem item : memberSellItem){
            itemNm.add(item.getItem().getItemNm());
            Member buyer = memberService.findMember(Long.valueOf(item.getCreatedBy()));
            buyerNm.add(buyer.getNickName());
        }
        model.addAttribute("itemNm", itemNm);
        model.addAttribute("buyerNm", buyerNm);
        model.addAttribute("memberSellItem",memberSellItem);
        model.addAttribute("name", member.getNickName());
        model.addAttribute("selectUser", selectUser);
        return "shopAdmin/userSellShopRecord";
    }


}