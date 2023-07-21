package com.blue.bluearchive.board.controller;


import com.blue.bluearchive.admin.service.CategoryService;
import com.blue.bluearchive.board.dto.*;
import com.blue.bluearchive.board.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;


//전체 건희 추가
@Controller
@RequiredArgsConstructor
public class CommentController {
    private final BoardService boardService;
    private final CategoryService categoryService;
    private final CommentService commentService;
    private final CommentsCommentService commentsCommentService;
    private final CommentLikeHateService commentLikeHateService;
    private final CommentsCommentLikeHateService commentsCommentLikeHateService;


    @PostMapping(value = "/comment/new")
    public String commentNew(@Valid CommentFormDto commentFormDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("errorMessage","게시글 등록 중 오류");
            return "redirect:/board/Detail/"+commentFormDto.getBoardId().getBoardId();
        }
        try {
            commentService.save(commentFormDto);
        }catch (Exception e){
            System.out.println("================================댓글 작성중 오류===============================");
            model.addAttribute("errorMessage","게시글 등록 중 오류");
        }
        return "redirect:/board/Detail/"+commentFormDto.getBoardId().getBoardId();
    }



    @PostMapping("/comment/edit")
    public String Commentedit(@RequestParam("boardId") int boardId,@RequestParam("commentId") int commentId, @RequestParam("commentContent") String commentContent, Model model) {
        //작성내용이 없을경우 예외처리
        if(commentContent == null || commentContent.trim().isEmpty()) {
            throw new IllegalArgumentException("작성된 내용이 없습니다");
        }
        commentService.update(commentId,commentContent);
        return "redirect:/board/Detail/" + boardId;
    }

    @PostMapping(value = "/comment/delete")
    public String commentDelete(@RequestParam("boardId") int boardId, @RequestParam("commentId") int commentId, Model model){
        commentService.delete(commentId);
        return "redirect:/board/Detail/"+boardId;
    }




    @PostMapping(value = "/commentsComment/new")
    public String commentsCommentNew(@Valid CommentsCommentFormDto commentsCommentFormDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("errorMessage","게시글 등록 중 오류");
            return "redirect:/board/Detail/"+commentsCommentFormDto.getComment().getBoard().getBoardId();
        }
        try {
            commentsCommentService.save(commentsCommentFormDto);
        }catch (Exception e){
            System.out.println("================================대댓글 작성중 오류===============================");
            model.addAttribute("errorMessage","게시글 등록 중 오류");
            e.printStackTrace();
        }

        return "redirect:/board/Detail/"+commentsCommentFormDto.getComment().getBoard().getBoardId();
    }

    @PostMapping("/commentsComment/edit")
    public String commentsCommentEdit(@RequestParam("boardId") int boardId,@RequestParam("commentsCommentId") int commentsCommentId, @RequestParam("commentsCommentContent") String commentsCommentContent, Model model) {
        //작성내용이 없을경우 예외처리
        if(commentsCommentContent == null || commentsCommentContent.trim().isEmpty()) {
            throw new IllegalArgumentException("작성 내용이 없습니다");
        }

        commentsCommentService.update(commentsCommentId,commentsCommentContent);
        return "redirect:/board/Detail/" + boardId;
    }

    @PostMapping(value = "/commentsComment/delete")
    public String commentsCommentDelete(@RequestParam("boardId") int boardId, @RequestParam("commentsCommentId") int commentsCommentId, Model model){
        commentsCommentService.delete(commentsCommentId);
        return "redirect:/board/Detail/"+boardId;
    }
    @PostMapping("/commentlikeHate")
    @ResponseBody
    public CommentLikeHateDto handleCommentLikeHateRequest(@RequestBody CommentLikeHateDto commentLikeHateDto) {

        if (commentLikeHateDto.isHate()) {
            commentLikeHateService.hateComment(commentLikeHateDto.getCommentId(), commentLikeHateDto.getIdx());
        } else if (commentLikeHateDto.isLike()) {
            commentLikeHateService.likeComment(commentLikeHateDto.getCommentId(), commentLikeHateDto.getIdx());
        }

        int likeCount=commentService.getCommentLikeCount(commentLikeHateDto.getCommentId());
        int hateCount=commentService.getCommentHateCount(commentLikeHateDto.getCommentId());
        boolean isLike = commentLikeHateService.isLiked(commentLikeHateDto.getCommentId(), commentLikeHateDto.getIdx());
        boolean isHate = commentLikeHateService.isHated(commentLikeHateDto.getCommentId(), commentLikeHateDto.getIdx());
        return new CommentLikeHateDto(likeCount, hateCount, isLike, isHate);

    }


    @PostMapping("/commentsCommentlikeHate")
    @ResponseBody
    public CommentsCommentLikeHateDto handleCommentsCommentLikeHateRequest(@RequestBody CommentsCommentLikeHateDto commentsCommentLikeHateDto) {
        if (commentsCommentLikeHateDto.isHate()) {
            commentsCommentLikeHateService.hateCommentsComment(commentsCommentLikeHateDto.getCommentsCommentId(), commentsCommentLikeHateDto.getIdx());
        } else if (commentsCommentLikeHateDto.isLike()) {
            commentsCommentLikeHateService.likeCommentsComment(commentsCommentLikeHateDto.getCommentsCommentId(), commentsCommentLikeHateDto.getIdx());
        }

        int likeCount=commentsCommentService.getCommentsCommentLikeCount(commentsCommentLikeHateDto.getCommentsCommentId());
        int hateCount=commentsCommentService.getCommentsCommentsHateCount(commentsCommentLikeHateDto.getCommentsCommentId());
        boolean isLike = commentsCommentLikeHateService.isLiked(commentsCommentLikeHateDto.getCommentsCommentId(), commentsCommentLikeHateDto.getIdx());
        boolean isHate = commentsCommentLikeHateService.isHated(commentsCommentLikeHateDto.getCommentsCommentId(), commentsCommentLikeHateDto.getIdx());
        return new CommentsCommentLikeHateDto(likeCount,hateCount,isLike,isHate);

    }

}
