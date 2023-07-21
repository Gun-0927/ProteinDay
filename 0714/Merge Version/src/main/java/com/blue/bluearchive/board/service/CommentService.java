package com.blue.bluearchive.board.service;

import com.blue.bluearchive.board.dto.BoardFormDto;
import com.blue.bluearchive.board.dto.CommentDto;
import com.blue.bluearchive.board.dto.CommentFormDto;
import com.blue.bluearchive.board.dto.CommentsCommentDto;
import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.board.entity.CommentsComment;
import com.blue.bluearchive.board.repository.BoardRepository;
import com.blue.bluearchive.board.repository.CommentRepository;
import com.blue.bluearchive.board.repository.CommentsCommentRepository;
import com.blue.bluearchive.member.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.web.server.UnAuthenticatedServerOAuth2AuthorizedClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;
    private final CommentsCommentRepository commentsCommentRepository;


    @Transactional(readOnly = true)
    public List<CommentDto> getCommentByBoardId(int boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        if (board == null) {
            // 예외 처리 또는 오류 처리
            return Collections.emptyList(); // 빈 목록 반환하거나 예외 처리 로직 추가
        }

        List<Comment> comments = commentRepository.findByBoard(board);
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            commentDtos.add(modelMapper.map(comment, CommentDto.class));
        }
        return commentDtos;
    }



    //건희 추가
    @Transactional(readOnly = false)
    public Integer save(CommentFormDto commentFormDto) throws  Exception{
        Comment comment = commentFormDto.createBoard();
        commentRepository.save(comment);
        return comment.getCommentId();
    }

    //건희 추가
    @Transactional(readOnly = false)
    public void update(int commentId, String commentContent){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Object principal = authentication.getPrincipal();
        CustomUserDetails userDetails = (CustomUserDetails) principal;
        String  loginMemberIdx=Long.toString(userDetails.getIdx());

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new IllegalArgumentException("유효하지않은 ID입니다"));
        if(!comment.getCreatedBy().equals(loginMemberIdx)) {
            throw new AccessDeniedException("현재 로그인되어있는 사용자와 작성자가 일치하지 않습니다");
        }
        comment.setCommentContent(commentContent);
    }

    //건희추가
    @Transactional(readOnly = false)
    public void delete(int commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new IllegalArgumentException("유효하지않은 ID입니다"));
        //대댓글 여부 확인하고 전부 삭제
        List<CommentsComment> commentsComments = commentsCommentRepository.findByComment(comment);
        if (commentsComments == null || commentsComments.isEmpty()) {
            System.out.println("====해당 Comment에 연결된 CommentsComment가 없습니다.====");
        } else {
            for(CommentsComment commentsComment : commentsComments){
                commentsCommentRepository.delete(commentsComment);
            }
        }
        commentRepository.delete(comment);
    }
    public void incrementCommentLikeCount(int commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Comment not found"));
        if (comment != null) {
            comment.setCommentLikeCount(comment.getCommentLikeCount() + 1);
            commentRepository.save(comment);
        }
    }
    public void incrementCommentHateCount(int commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Comment not found"));
        if (comment != null) {
            comment.setCommentHateCount(comment.getCommentHateCount() + 1);
            commentRepository.save(comment);
        }
    }
    public void decreaseCommentLikeCount(int commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Comment not found"));
        if (comment != null) {
            comment.setCommentLikeCount(comment.getCommentLikeCount() - 1);
            commentRepository.save(comment);
        }
    }
    public void decreaseCommentHateCount(int commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Comment not found"));
        if (comment != null) {
            comment.setCommentHateCount(comment.getCommentHateCount() - 1);
            commentRepository.save(comment);
        }
    }
    public int getCommentLikeCount(int commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        return (comment != null) ? comment.getCommentLikeCount() : 0;
    }
    public int getCommentHateCount(int commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        return (comment != null) ? comment.getCommentHateCount() : 0;
    }


}
