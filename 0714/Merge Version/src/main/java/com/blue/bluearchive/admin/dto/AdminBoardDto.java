package com.blue.bluearchive.admin.dto;

import com.blue.bluearchive.admin.entity.Category;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminBoardDto {
    private int boardId;
    private String boardContent;
    private Integer boardCount;
    private String boardCreatedBy;
    private Integer boardHateCount;
    private Integer boardLikeCount;
    private Integer boardReportsCount;
    private LocalDateTime regTime;
    private String boardTitle;
    private Category category;
    private Integer notReportCount;
}
