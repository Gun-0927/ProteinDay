package com.blue.bluearchive.board.dto;

import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentsCommentDto {
    private int commentsCommentId;
    private Comment comment;
    private String commentsCommentContent;
    private Integer commentsCommentLikeCount = 0;

    private boolean commentsCommentIsLike;
    private boolean commentsCommentIsHate;

    private Integer commentsCommentHateCount = 0;
    private Integer commentsCommentReportsCount = 0;
    private String createdBy;
    private LocalDateTime regTime;
}
