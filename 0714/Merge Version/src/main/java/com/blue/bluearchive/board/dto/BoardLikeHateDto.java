package com.blue.bluearchive.board.dto;

import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.member.entity.Member;
import lombok.Data;

@Data
public class BoardLikeHateDto {
    private Integer likeHateId;
    private boolean like;
    private boolean hate;
    private int boardId;
    private Long idx;
    private int likeCount;
    private int hateCount;

    public BoardLikeHateDto(int likeCount, int hateCount,boolean isLiked, boolean isHated) {
        this.likeCount = likeCount;
        this.hateCount = hateCount;
        this.like=isLiked;
        this.hate=isHated;


    }


}
