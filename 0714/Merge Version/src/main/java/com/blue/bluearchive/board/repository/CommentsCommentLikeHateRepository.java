package com.blue.bluearchive.board.repository;

import com.blue.bluearchive.board.entity.CommentsCommentLikeHate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsCommentLikeHateRepository extends JpaRepository<CommentsCommentLikeHate,Integer> {

    CommentsCommentLikeHate findByCommentsCommentCommentsCommentIdAndMemberIdx(int commentsCommentId,Long idx);
}
