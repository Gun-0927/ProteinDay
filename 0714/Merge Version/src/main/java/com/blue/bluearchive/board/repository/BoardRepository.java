package com.blue.bluearchive.board.repository;

import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer>, QuerydslPredicateExecutor<Board>
        ,BoardRepositoryCustom {
    List<Board> findByCategory(Category category);

    //유저페이지사용 건희추가
    List<Board> findByCreatedBy(String createdBy);

    Page<Board> findByBoardLikeCountGreaterThanEqual(int likeCount, Pageable pageable);


}
