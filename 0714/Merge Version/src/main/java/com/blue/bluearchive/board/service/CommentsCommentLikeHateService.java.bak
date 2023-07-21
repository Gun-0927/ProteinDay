package com.blue.bluearchive.board.service;

import com.blue.bluearchive.board.dto.CommentLikeHateDto;
import com.blue.bluearchive.board.dto.CommentsCommentLikeHateDto;
import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.board.entity.CommentLikeHate;
import com.blue.bluearchive.board.entity.CommentsComment;
import com.blue.bluearchive.board.entity.CommentsCommentLikeHate;
import com.blue.bluearchive.board.repository.CommentLikeHateRepository;
import com.blue.bluearchive.board.repository.CommentRepository;
import com.blue.bluearchive.board.repository.CommentsCommentLikeHateRepository;
import com.blue.bluearchive.board.repository.CommentsCommentRepository;
import com.blue.bluearchive.member.entity.Member;
import com.blue.bluearchive.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentsCommentLikeHateService {
    private final CommentsCommentLikeHateRepository commentsCommentLikeHateRepository;
    private final CommentsCommentRepository commentsCommentRepository;
    private final MemberRepository memberRepository;
    private final CommentsCommentService commentsCommentService;
    public CommentsCommentLikeHateDto likeCommentsComment(int commentsCommentId, Long idx) {
        CommentsCommentLikeHate likeHate = commentsCommentLikeHateRepository.findByCommentsCommentCommentsCommentIdAndMemberIdx(commentsCommentId, idx);
        CommentsComment commentsComment = commentsCommentRepository.findById(commentsCommentId).orElse(null);
        Member member = memberRepository.findByIdx(idx);

        if (likeHate != null) {
            // 이미 좋아요를 눌렀을 경우, 좋아요 취소
            if (likeHate.isLike()) {
                likeHate.setLike(false);
                commentsCommentService.decreaseCommentsCommentLikeCount(commentsCommentId);
                commentsCommentLikeHateRepository.save(likeHate);
            } else {
                if (likeHate.isHate()) {
                    likeHate.setLike(false);
                    commentsCommentLikeHateRepository.save(likeHate);
                } else {
                    likeHate.setLike(true);
                    commentsCommentService.incrementCommentsCommentLikeCount(commentsCommentId);
                    commentsCommentLikeHateRepository.save(likeHate);
                }
            }
        }else {
            likeHate = new CommentsCommentLikeHate();
            likeHate.setCommentsComment(commentsComment);
            likeHate.setMember(member);
            likeHate.setLike(true);
            commentsCommentService.incrementCommentsCommentLikeCount(commentsCommentId);
            commentsCommentLikeHateRepository.save(likeHate);
        }
        int likeCount = commentsCommentService.getCommentsCommentLikeCount(commentsCommentId);
        int hateCount = commentsCommentService.getCommentsCommentsHateCount(commentsCommentId);

        return new CommentsCommentLikeHateDto(likeCount, hateCount);
    }

    public CommentsCommentLikeHateDto hateCommentsComment(int commentsCommentId, Long idx) {
        CommentsCommentLikeHate likeHate = commentsCommentLikeHateRepository.findByCommentsCommentCommentsCommentIdAndMemberIdx(commentsCommentId, idx);
        CommentsComment commentsComment = commentsCommentRepository.findById(commentsCommentId).orElse(null);
        Member member = memberRepository.findByIdx(idx);

        if (likeHate != null) {
            if (likeHate.isHate()) {
                likeHate.setHate(false);

                commentsCommentService.decreaseCommentsCommentHateCount(commentsCommentId);
                commentsCommentLikeHateRepository.save(likeHate);
            } else {
                if (likeHate.isLike()) {

                    likeHate.setHate(false);
                    commentsCommentLikeHateRepository.save(likeHate);
                } else {

                    likeHate.setHate(true);
                    commentsCommentService.incrementCommentsCommentHateCount(commentsCommentId);
                    commentsCommentLikeHateRepository.save(likeHate);
                }
            }
        }else {
            likeHate = new CommentsCommentLikeHate();
            likeHate.setCommentsComment(commentsComment);
            likeHate.setMember(member);
            likeHate.setHate(true);
            commentsCommentService.incrementCommentsCommentHateCount(commentsCommentId);
            commentsCommentLikeHateRepository.save(likeHate);
        }
        int likeCount = commentsCommentService.getCommentsCommentLikeCount(commentsCommentId);
        int hateCount = commentsCommentService.getCommentsCommentsHateCount(commentsCommentId);

        return new CommentsCommentLikeHateDto(likeCount, hateCount);
    }

}
