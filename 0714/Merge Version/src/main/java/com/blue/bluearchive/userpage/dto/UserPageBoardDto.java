package com.blue.bluearchive.userpage.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserPageBoardDto {

    private int boardId;
    private String boardTitle;
    private Integer commentCount;
    private Integer boardCount;
    private LocalDateTime regTime;
}
