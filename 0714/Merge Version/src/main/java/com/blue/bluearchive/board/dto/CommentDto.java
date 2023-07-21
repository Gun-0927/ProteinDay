package com.blue.bluearchive.board.dto;

import com.blue.bluearchive.board.entity.Board;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private int commentId;
    private Board boardId;
    private String commentContent;
    private Integer commentLikeCount = 0;

    private boolean commentIsLike;
    private boolean commentIsHate;

    private Integer commentHateCount = 0;
    private Integer commentReportsCount = 0;
    private String createdBy;
    private LocalDateTime regTime;
}
