package com.blue.bluearchive.admin.dto;

import com.blue.bluearchive.board.entity.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminCommentsCommentDto {
    private int commentsCommentId;
    private Comment comment;
    private String commentsCommentContent;
    private Integer commentsCommentLikeCount = 0;

    private Integer commentsCommentHateCount = 0;
    private Integer commentsCommentReportsCount = 0;
    private String createdBy;
    private LocalDateTime regTime;
    // 승훈 코드 추가
    private Integer notReportCount;
}
