package com.blue.bluearchive.board.service;

import com.blue.bluearchive.board.dto.BoardLikeHateDto;
import com.blue.bluearchive.board.dto.CommentLikeHateDto;
import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.BoardLikeHate;
import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.board.entity.CommentLikeHate;
import com.blue.bluearchive.board.repository.CommentLikeHateRepository;
import com.blue.bluearchive.board.repository.CommentRepository;
import com.blue.bluearchive.member.entity.Member;
import com.blue.bluearchive.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentLikeHateService {
    private final CommentLikeHateRepository commentLikeHateRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final CommentService commentService;
    public CommentLikeHateDto likeComment(int commentId, Long idx) {
        CommentLikeHate likeHate = commentLikeHateRepository.findByCommentCommentIdAndMemberIdx(commentId, idx);
        Comment comment = commentRepository.findById(commentId).orElse(null);
        Member member = memberRepository.findByIdx(idx);

        if (likeHate != null) {
            // 이미 좋아요를 눌렀을 경우, 좋아요 취소
            if (likeHate.isLike()) {
                likeHate.setLike(false);
                commentService.decreaseCommentLikeCount(commentId);
                commentLikeHateRepository.save(likeHate);
            } else {
                if (likeHate.isHate()) {
                    likeHate.setLike(false);
                    commentLikeHateRepository.save(likeHate);
                } else {
                    likeHate.setLike(true);
                    commentService.incrementCommentLikeCount(commentId);
                    commentLikeHateRepository.save(likeHate);
                }
            }
        }else {
            likeHate = new CommentLikeHate();
            likeHate.setComment(comment);
            likeHate.setMember(member);
            likeHate.setLike(true);
            commentService.incrementCommentLikeCount(commentId);
            commentLikeHateRepository.save(likeHate);
        }
        int likeCount = commentService.getCommentLikeCount(commentId);
        int hateCount = commentService.getCommentHateCount(commentId);

        return new CommentLikeHateDto(likeCount, hateCount,likeHate.isLike(),likeHate.isHate());
    }

    public CommentLikeHateDto hateComment(int commentId, Long idx) {
        CommentLikeHate likeHate = commentLikeHateRepository.findByCommentCommentIdAndMemberIdx(commentId, idx);
        Comment comment = commentRepository.findById(commentId).orElse(null);
        Member member = memberRepository.findByIdx(idx);

        if (likeHate != null) {
            if (likeHate.isHate()) {
                likeHate.setHate(false);
                commentService.decreaseCommentHateCount(commentId);
                commentLikeHateRepository.save(likeHate);
            } else {
                if (likeHate.isLike()) {
                    likeHate.setHate(false);
                    commentLikeHateRepository.save(likeHate);
                } else {
                    likeHate.setHate(true);
                    commentService.incrementCommentHateCount(commentId);
                    commentLikeHateRepository.save(likeHate);
                }
            }
        }else {
            likeHate = new CommentLikeHate();
            likeHate.setComment(comment);
            likeHate.setMember(member);
            likeHate.setHate(true);
            commentService.incrementCommentHateCount(commentId);
            commentLikeHateRepository.save(likeHate);
        }
        int likeCount = commentService.getCommentLikeCount(commentId);
        int hateCount = commentService.getCommentHateCount(commentId);

        return new CommentLikeHateDto(likeCount, hateCount,likeHate.isLike(),likeHate.isHate());
    }

    public boolean isLiked(int commentId, Long idx) {
        CommentLikeHate commentLikeHate = commentLikeHateRepository.findByCommentCommentIdAndMemberIdx(commentId, idx);
        return commentLikeHate != null && commentLikeHate.isLike();
    }

    public boolean isHated(int commentId, Long idx) {
        CommentLikeHate commentLikeHate = commentLikeHateRepository.findByCommentCommentIdAndMemberIdx(commentId, idx);
        return commentLikeHate != null && commentLikeHate.isHate();
    }
}
