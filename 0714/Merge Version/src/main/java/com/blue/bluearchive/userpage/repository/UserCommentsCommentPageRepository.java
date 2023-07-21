package com.blue.bluearchive.userpage.repository;

import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.board.entity.CommentsComment;
import com.blue.bluearchive.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCommentsCommentPageRepository extends JpaRepository<CommentsComment, Integer>, QuerydslPredicateExecutor<CommentsComment>
        , UserCommentsCommentPageRepositoryCustom{

    List<CommentsComment> findByCreatedBy(String createdBy);


    List<CommentsComment> findByCreatedByAndCommentCommentContentContaining(String createdBy, String commentContent);
    List<CommentsComment> findByCreatedByAndCommentsCommentContentContaining(String createdBy, String commentsCommentContent);


}
