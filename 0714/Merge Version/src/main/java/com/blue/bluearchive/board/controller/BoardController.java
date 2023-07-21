package com.blue.bluearchive.board.controller;

import com.blue.bluearchive.admin.service.CategoryService;
import com.blue.bluearchive.admin.dto.CategoryDto;
import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.board.dto.*;
import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.board.repository.BoardRepository;
import com.blue.bluearchive.board.repository.CommentRepository;
import com.blue.bluearchive.board.repository.CommentsCommentRepository;
import com.blue.bluearchive.board.service.*;
import com.blue.bluearchive.member.dto.CustomUserDetails;
import com.blue.bluearchive.member.entity.Member;
import com.blue.bluearchive.member.repository.MemberRepository;
import com.blue.bluearchive.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CategoryService categoryService;

    private final CommentService commentService;

    private final CommentsCommentService commentsCommentService;

    private final MemberRepository memberRepository;

    private final BoardRepository boardRepository;

    private final BoardLikeHateService boardLikeHateService;

    private final CommentLikeHateService commentLikeHateService;

    private final CommentsCommentLikeHateService commentsCommentLikeHateService;

    private final MemberService memberService;

    private final CommentRepository commentRepository;

    private final CommentsCommentRepository commentsCommentRepository;
//    private String[] boardImgFiles; // boardImgFiles 필드


    private void processCommentCounts(Page<Board> boardList) {
        for (Board board : boardList) {
            int boardId = board.getBoardId();

            List<CommentDto> commentDtoList = commentService.getCommentByBoardId(boardId);
            Map<Integer, List<CommentsCommentDto>> commentsCommentMap = new HashMap<>();

            int totalCommentCount = 0;

            for (CommentDto commentDto : commentDtoList) {
                int commentId = commentDto.getCommentId();
                List<CommentsCommentDto> commentsCommentDtoList = commentsCommentService.getCommentsCommentByCommentId(commentId);
                commentsCommentMap.put(commentId, commentsCommentDtoList);

                totalCommentCount += commentsCommentDtoList.size();
            }

            int commentCount = commentDtoList.size() + totalCommentCount;
            board.setCommentCount(commentCount);
            boardService.updateCommentCount(board);
        }
    }
    @GetMapping(value = {"/board/all","/board/all/{page}"})
    public String getBoardList(BoardSearchDto boardSearchDto,
                               @PathVariable("page") Optional<Integer> page,Model model) {
//        List<BoardDto> boardList = boardService.getAllBoards();
//        model.addAttribute("boardList", boardList);
        List<CategoryDto> categoryList = categoryService.getAllCategory();
        model.addAttribute("categoryList", categoryList);

        Pageable pageable = PageRequest.of(page.isPresent()?page.get():0,20);
        Page<Board> boards = boardService.getBoardPage(boardSearchDto,pageable);
        processCommentCounts(boards);
        List<String> createdBy = new ArrayList<>();
        for(Board board : boards){
            Member nickname = memberService.findMember(Long.valueOf(board.getCreatedBy()));
            createdBy.add(nickname.getNickName());
        }
        // 로그인된 사용자의 memberIdx 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long memberIdx = null; // 초기값을 null로 설정


        if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            memberIdx = userDetails.getIdx();
        }
        model.addAttribute("memberIdx",memberIdx);
        model.addAttribute("createdBy",createdBy);
        model.addAttribute("boards",boards);
        model.addAttribute("boardSearchDto",boardSearchDto);
        model.addAttribute("maxPage",20);


//        return "board/list";
        return "board/testmain";
    }


    // POST 방식으로 "/board/{categoryId}" 경로에 접근할 때의 처리
    @GetMapping(value = {"/board/{categoryId}","/board/{categoryId}/{page}"})
    public String getBoardsByCategory(@PathVariable int categoryId, BoardSearchDto boardSearchDto,
                                      @PathVariable("page") Optional<Integer> page,Model model) {
        Category category = categoryService.getCategoryById(categoryId); // categoryId에 해당하는 카테고리를 가져옵니다.
//        List<BoardDto> boardList = boardService.getBoardsByCategory(category); // 해당 카테고리에 속한 게시물들을 가져옵니다.
//        model.addAttribute("boardList", boardList); // boardList를 모델에 추가합니다.
        //processCommentCounts(boardList);
        Pageable pageable = PageRequest.of(page.isPresent()?page.get():0,20);
        Page<Board> boards = boardService.getCategoryBoardPage(category,boardSearchDto,pageable);
        processCommentCounts(boards);
        List<String> createdBy = new ArrayList<>();
        for(Board board : boards){
            Member nickname = memberService.findMember(Long.valueOf(board.getCreatedBy()));
            createdBy.add(nickname.getNickName());
        }
        model.addAttribute("createdBy",createdBy);
        // 로그인된 사용자의 memberIdx 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long memberIdx = null; // 초기값을 null로 설정


        if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            memberIdx = userDetails.getIdx();
        }
        model.addAttribute("memberIdx",memberIdx);

        model.addAttribute("categoryId", categoryId);
        model.addAttribute("boards",boards);
        model.addAttribute("boardSearchDto",boardSearchDto);
        model.addAttribute("maxPage",20);


        List<CategoryDto> categoryList = categoryService.getAllCategory(); // 모든 카테고리를 가져옵니다.
        model.addAttribute("categoryList", categoryList); // categoryList를 모델에 추가합니다.


//        return "board/list"; // board/list 뷰를 반환합니다.
        return "board/testmain";
    }
    @GetMapping("/board/Detail/{boardId}")
    public String getBoardDetails(@PathVariable int boardId, Model model) {
        boardService.incrementBoardCount(boardId);
        BoardDto board = boardService.getBoardById(boardId);
        model.addAttribute("board", board);
        Member boardCreated = memberService.findMember(Long.valueOf(board.getBoardCreatedBy()));
        String boardCreatedBy = boardCreated.getNickName();
        model.addAttribute("boardCreatedBy",boardCreatedBy);
        BoardFormDto boardFormDto= boardService.getBoardImgById(boardId);
        model.addAttribute("boardFormDto",boardFormDto);
        // 로그인된 사용자의 memberIdx 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long memberIdx = null; // 초기값을 null로 설정
        String memberNickname = null;

        if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            memberIdx = userDetails.getIdx();
            memberNickname = userDetails.getNickName();
        }

        boolean isLike = false;
        boolean isHate = false;
        boolean commentIsLike = false;
        boolean commentIsHate = false;
        boolean commentsCommentIsLike = false;
        boolean commentsCommentIsHate = false;
        List<String> commentCreatedBy = new ArrayList<>();
        List<String> commentsCommentCreatedBy = new ArrayList<>();
        if (memberIdx != null) {
            // 로그인된 사용자인 경우에만 isLike, isHate 값을 가져옴
            isLike = boardLikeHateService.isLiked(boardId, memberIdx);
            isHate = boardLikeHateService.isHated(boardId, memberIdx);
        }
        List<CommentDto> commentList = commentService.getCommentByBoardId(boardId);

        Map<Integer, List<CommentsCommentDto>> commentsCommentMap = new HashMap<>();
        for (CommentDto comment : commentList) {
            int commentId = comment.getCommentId();
            Member commentCreate = memberService.findMember(Long.valueOf(comment.getCreatedBy()));
            commentCreatedBy.add(commentCreate.getNickName());
            if (memberIdx != null) {
                // 로그인된 사용자인 경우에만 isLike, isHate 값을 가져옴
                commentIsLike =commentLikeHateService.isLiked(commentId, memberIdx);
                commentIsHate =commentLikeHateService.isHated(commentId, memberIdx);
                comment.setCommentIsLike(commentIsLike);
                comment.setCommentIsHate(commentIsHate);

            }
            model.addAttribute("commentList", commentList);
            model.addAttribute("commentCreatedBy",commentCreatedBy);
            List<CommentsCommentDto> commentsCommentList = commentsCommentService.getCommentsCommentByCommentId(commentId);
            for(CommentsCommentDto commentsComment :commentsCommentList ){
                int commentsCommentId = commentsComment.getCommentsCommentId();
                Member commentsCommentCreate = memberService.findMember(Long.valueOf(commentsComment.getCreatedBy()));
                commentsCommentCreatedBy.add(commentsCommentCreate.getNickName());
                if(memberIdx != null){
                    commentsCommentIsLike=commentsCommentLikeHateService.isLiked(commentsCommentId,memberIdx);
                    commentsCommentIsHate=commentsCommentLikeHateService.isHated(commentsCommentId,memberIdx);
                    commentsComment.setCommentsCommentIsLike(commentsCommentIsLike);
                    commentsComment.setCommentsCommentIsHate(commentsCommentIsHate);
                }
            }
            commentsCommentMap.put(commentId, commentsCommentList);


        }
        List<Object[]> best = commentRepository.findCommentsAndCommentsCommentByBoardId(boardId);
        model.addAttribute("best",best);


        List<Object[]> bestIsLikeIsHate = new ArrayList<>();
        List<String> bestCommentCratedBy = new ArrayList<>();

        for (Object[] row : best) {
            Integer data = (Integer) row[1];
            Object type = row[0];
            boolean bestIsLike = false;
            boolean bestIsHate = false;

            if (type.equals("comment")) {
                bestIsLike = commentLikeHateService.isLiked( data, memberIdx);
                bestIsHate = commentLikeHateService.isHated( data, memberIdx);
            } else if (type.equals("commentscomment")) {
                bestIsLike = commentsCommentLikeHateService.isLiked( data, memberIdx);
                bestIsHate = commentsCommentLikeHateService.isHated( data, memberIdx);
            }

            Object[] resultRow = {bestIsLike, bestIsHate};
            bestIsLikeIsHate.add(resultRow);

            Member bestCommentCreate = memberService.findMember(Long.valueOf((String) row[4]));
            bestCommentCratedBy.add(bestCommentCreate.getNickName());
        }
        model.addAttribute("bestCommentCratedBy",bestCommentCratedBy);
        model.addAttribute("bestIsLikeIsHate",bestIsLikeIsHate);
        model.addAttribute("memberIdx",memberIdx);
        model.addAttribute("memberNickname",memberNickname);
        model.addAttribute("isLike", isLike);
        model.addAttribute("isHate", isHate);
        model.addAttribute("commentsCommentMap", commentsCommentMap);
        model.addAttribute("commentsCommentCreatedBy",commentsCommentCreatedBy);

//        return "board/boardDetail_HAN";
        return "board/testDetail";
    }
    @GetMapping(value = "/board/Write/new")
    public String getBoardWrite(Model model){
        List<CategoryDto> categoryList = categoryService.getAllCategory();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("boardFormDto",new BoardFormDto());
        return "board/boardWrite3";
    }
    @PostMapping(value = "/board/Write/new")
    public String boardNew(@Valid BoardFormDto boardFormDto, BindingResult bindingResult, Model model
            ,@RequestParam(value = "boardImgFile",required = false)List<MultipartFile>boardImgFileList){
        if(bindingResult.hasErrors()){
            model.addAttribute("errorMessage","게시글 등록 중 오류");
            return "/board/boardWrite3";
        }
        try {

            boardService.saveBoard(boardFormDto, boardImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "게시글 등록 중 오류");

        }

        return "redirect:/board/all/"+boardFormDto.getCategory().getCategoryId();
    }
    @GetMapping(value = "board/Edit/{boardId}")
    public String boardEditForm(@PathVariable("boardId")int boardId, Model model){
        try{
            List<CategoryDto> categoryList = categoryService.getAllCategory();
            model.addAttribute("categoryList", categoryList);

            BoardFormDto boardFormDto= boardService.getBoardImgById(boardId);
            //카테고리 선택 추가
            int selectCategoryId= boardFormDto.getCategory().getCategoryId();
            model.addAttribute("selectCategoryId",selectCategoryId);

            model.addAttribute("boardFormDto",boardFormDto);

            List<BoardImgDto> imageList = boardFormDto.getBoardImgDtoList(); // 이미지 목록을 가져온다고 가정합니다.
            List<String> imageUrls = new ArrayList<>();
            for (BoardImgDto image : imageList) {
                imageUrls.add(image.getBoardImgUrl()); // 이미지 URL 정보를 가져온다고 가정합니다.
            }
            model.addAttribute("imageUrls", imageUrls); // 이미지 URL 목록을 모델에 추가하여 View로 전달합니다.



        }catch (EntityNotFoundException e){
            model.addAttribute("errorMessage","오류");
            return  "redirect:/board/Detail/"+boardId;
        }
        return "/board/boardWrite3";
    }
    @PostMapping(value = "/board/Edit/{boardId}")
    public String boardUpdate(BoardFormDto boardFormDto,BindingResult bindingResult
            ,@RequestParam(value = "boardImgFile",required = false)List<MultipartFile>boardImgFileList,@RequestParam(value = "boardImgUrl",required = false)List<String>boardImgUrlList,
                              Model model){
        if(bindingResult.hasErrors()){
            return "board/boardWrite3";
        }
        try{
            boardService.updateBoard(boardFormDto,boardImgUrlList,boardImgFileList);
        }catch (Exception e){
            model.addAttribute("errorMessage","게시글 수정 중 에러 발생");
            return "board/boardWrite3";
        }
        return "redirect:/board/Detail/"+boardFormDto.getBoardId();
    }
    @PostMapping("/boardlikeHate")
    @ResponseBody
    public BoardLikeHateDto handleBoardLikeHateRequest(@RequestBody BoardLikeHateDto boardLikeHateDto) {

        if (boardLikeHateDto.isHate()) {
            boardLikeHateService.hateBoard(boardLikeHateDto.getBoardId(), boardLikeHateDto.getIdx());
        } else if (boardLikeHateDto.isLike()) {
            boardLikeHateService.likeBoard(boardLikeHateDto.getBoardId(), boardLikeHateDto.getIdx());
        }
        int likeCount = boardService.getBoardLikeCount(boardLikeHateDto.getBoardId());
        int hateCount = boardService.getBoardHateCount(boardLikeHateDto.getBoardId());
        boolean isLike = boardLikeHateService.isLiked(boardLikeHateDto.getBoardId(), boardLikeHateDto.getIdx());
        boolean isHate = boardLikeHateService.isHated(boardLikeHateDto.getBoardId(), boardLikeHateDto.getIdx());

        return new BoardLikeHateDto(likeCount, hateCount, isLike, isHate);
    }


    @GetMapping(value = "/board/Delete/{boardId}")
    public String deleteBoard(@PathVariable int boardId){
        Board board = boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);
        boardRepository.delete(board);

        return "redirect:/board/"+board.getCategory().getCategoryId();
    }
    @GetMapping(value = {"/board/chuchu","/board/chuchu/{page}"})
    public String getChuChuBoardList(BoardSearchDto boardSearchDto,
                                     @PathVariable("page") Optional<Integer> page,Model model) {

        List<CategoryDto> categoryList = categoryService.getAllCategory();
        model.addAttribute("categoryList", categoryList);

        Sort sort = Sort.by(Sort.Direction.DESC, "boardId"); // ID 값을 기준으로 내림차순 정렬
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 20, sort);
        Page<Board> boards = boardService.getBoardsWithLikesGreaterThanEqual(30, pageable);
        model.addAttribute("boards", boards);
        List<String> createdBy = new ArrayList<>();
        for(Board board : boards){
            Member nickname = memberService.findMember(Long.valueOf(board.getCreatedBy()));
            createdBy.add(nickname.getNickName());
        }
        model.addAttribute("createdBy",createdBy);

        processCommentCounts(boards);

        model.addAttribute("boards",boards);
        model.addAttribute("boardSearchDto",boardSearchDto);
        model.addAttribute("maxPage",20);
        model.addAttribute("chuchu",1);

//        return "board/list";
        return "board/testmain";
    }


}