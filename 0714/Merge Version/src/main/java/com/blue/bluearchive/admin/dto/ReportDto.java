package com.blue.bluearchive.admin.dto;

import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.board.entity.CommentsComment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportDto {
    private int reportId;
    private String createdBy;
    private String reportCategory;
    private LocalDateTime regTime;
    private String reportContent;
    private Board board;
    private boolean reportStatus;
    private Comment comment;
    private CommentsComment commentsComment;
}
