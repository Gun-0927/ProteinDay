package com.blue.bluearchive.board.repository;

import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.board.entity.CommentsComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsCommentRepository extends JpaRepository<CommentsComment,Integer> {
    List<CommentsComment> findByComment(Comment comment);

    //유저페이지 사용 건희 추가
    List<CommentsComment> findByCreatedBy(String CreatedBy);

   // int countByCommentId(Comment commentId);
}
