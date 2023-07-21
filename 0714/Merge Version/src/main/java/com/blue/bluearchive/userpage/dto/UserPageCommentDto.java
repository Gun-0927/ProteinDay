package com.blue.bluearchive.userpage.dto;


import com.blue.bluearchive.board.entity.Board;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserPageCommentDto {
    private int commentId;
    private Board board;
    private String commentContent;

    private String createdBy;
    private LocalDateTime regTime;

    private int commentsCommentCount;
}
