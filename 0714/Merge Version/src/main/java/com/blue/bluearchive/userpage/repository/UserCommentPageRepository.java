package com.blue.bluearchive.userpage.repository;


import com.blue.bluearchive.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCommentPageRepository extends JpaRepository<Comment, Integer>, QuerydslPredicateExecutor<Comment>
        , UserCommentPageRepositoryCustom{
}
