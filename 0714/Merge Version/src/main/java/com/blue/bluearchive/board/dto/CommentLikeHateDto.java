package com.blue.bluearchive.board.dto;

import lombok.Data;

@Data
public class CommentLikeHateDto {
    private Integer likeHateId;
    private boolean like;
    private boolean hate;
    private int commentId;
    private Long idx;
    private int likeCount;
    private int hateCount;
    public CommentLikeHateDto(int likeCount, int hateCount,boolean isLiked, boolean isHated) {
        this.likeCount = likeCount;
        this.hateCount = hateCount;
        this.like=isLiked;
        this.hate=isHated;
    }
}
