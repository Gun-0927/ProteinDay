package com.blue.bluearchive.board.repository;

import com.blue.bluearchive.board.entity.CommentLikeHate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeHateRepository extends JpaRepository<CommentLikeHate,Integer> {
    CommentLikeHate findByCommentCommentIdAndMemberIdx(int commentId,Long idx);


}
