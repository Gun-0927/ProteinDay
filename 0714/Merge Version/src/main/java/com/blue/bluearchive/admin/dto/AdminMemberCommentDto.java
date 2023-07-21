package com.blue.bluearchive.admin.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminMemberCommentDto {

    private String commentContent;
    private Integer likeCnt;
    private Integer hateCnt;
    private Integer reoortCnt;
    private LocalDateTime regTime;
}
