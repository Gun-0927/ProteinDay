package com.blue.bluearchive.board.dto;

import lombok.Data;

@Data
public class CommentsCommentLikeHateDto {
    private Integer likeHateId;
    private boolean like;
    private boolean hate;
    private int commentsCommentId;
    private Long idx;
    private int likeCount;
    private int hateCount;
    public CommentsCommentLikeHateDto(int likeCount, int hateCount,boolean isLiked, boolean isHated) {
        this.likeCount = likeCount;
        this.hateCount = hateCount;
        this.like=isLiked;
        this.hate=isHated;
    }
}
