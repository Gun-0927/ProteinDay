package com.blue.bluearchive.board.repository;

import com.blue.bluearchive.board.dto.CommentDto;
import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByBoard(Board boardId);

    @Query(value = "SELECT 'comment' AS type, c.* " +
            "FROM comment c " +
            "WHERE c.board_id = :boardId And comment_like_count>=30 " +
            "UNION ALL " +
            "SELECT 'commentscomment' AS type, cc.* " +
            "FROM comments_comment cc " +
            "INNER JOIN comment c ON cc.comment_id = c.comment_id " +
            "WHERE c.board_id = :boardId And cc.comments_comment_like_count>=30 " +
            "ORDER BY comment_like_count DESC " +
            "Limit 4",
            nativeQuery = true)
    List<Object[]> findCommentsAndCommentsCommentByBoardId(@Param("boardId") int boardId);


    int countByBoard(Board boardId);

    //유저페이지사용 건희추가
    List<Comment> findByCreatedBy(String CreatedBy);



}
