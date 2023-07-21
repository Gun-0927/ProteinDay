package com.blue.bluearchive.admin.repository;

import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminCommentRepository extends JpaRepository<Comment,Integer>, QuerydslPredicateExecutor<Comment>
        , AdminCommentRepositoryCustom {



    List<Comment> findByCreatedBy(String createdBy);


}
