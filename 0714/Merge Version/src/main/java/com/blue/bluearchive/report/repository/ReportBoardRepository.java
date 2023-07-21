package com.blue.bluearchive.report.repository;

import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.board.entity.CommentsComment;
import com.blue.bluearchive.report.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportBoardRepository extends JpaRepository<Report,Integer> {

    //건희 추가 승훈이꺼에 사용용도
    long countByBoardBoardIdAndReportStatusFalse(int boardId);

    //승훈 추가
    List<Report> findByBoardBoardIdAndReportStatusFalse(int boardId);

    List<Report> findByCommentCommentIdAndReportStatusFalse(int commentId);

    List<Report> findByCommentsCommentCommentsCommentIdAndReportStatusFalse(int commentsId);


    Page<Report> findByCommentsCommentAndReportStatusOrderByRegTimeDesc(CommentsComment commentsComment, boolean b, Pageable pageable);
    Page<Report> findByCommentAndReportStatusOrderByRegTimeDesc(Comment comment, boolean b, Pageable pageable);
    Page<Report> findByBoardAndReportStatusOrderByRegTimeDesc(Board board, boolean b, Pageable pageable);

    List<Report> findByCommentsCommentAndReportStatusOrderByRegTimeDesc(CommentsComment commentsComment, boolean b);
    List<Report> findByCommentAndReportStatusOrderByRegTimeDesc(Comment comment, boolean b);
    List<Report> findByBoardAndReportStatusOrderByRegTimeDesc(Board board, boolean b);

    //멤 버 신고수 적립 시스템
    Page<Report> findByCommentsCommentInAndReportStatusOrderByRegTimeDesc(List<CommentsComment> commentsComment, boolean b,Pageable pageable);
    Page<Report> findByCommentInAndReportStatusOrderByRegTimeDesc(List<Comment> comment, boolean b, Pageable pageable);
    Page<Report> findByBoardInAndReportStatusOrderByRegTimeDesc(List<Board> board, boolean b, Pageable pageable);

    List<Report> findByCommentsCommentInAndReportStatusOrderByRegTimeDesc(List<CommentsComment> commentsComment, boolean b);
    List<Report> findByCommentInAndReportStatusOrderByRegTimeDesc(List<Comment> comment, boolean b);
    List<Report> findByBoardInAndReportStatusOrderByRegTimeDesc(List<Board> board, boolean b);
}
