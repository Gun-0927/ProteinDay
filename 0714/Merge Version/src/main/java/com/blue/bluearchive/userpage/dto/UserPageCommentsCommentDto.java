package com.blue.bluearchive.userpage.dto;

import com.blue.bluearchive.board.entity.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserPageCommentsCommentDto {
    private int commentsCommentId;
    private String commentsCommentContent;

    private Comment comment;

    private String createdBy;
    private LocalDateTime regTime;

}
