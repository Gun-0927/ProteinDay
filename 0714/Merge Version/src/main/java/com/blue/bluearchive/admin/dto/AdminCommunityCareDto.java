package com.blue.bluearchive.admin.dto;


import com.blue.bluearchive.admin.entity.Category;
import lombok.Data;

@Data
public class AdminCommunityCareDto {
    private int boardCount;
    private int commentCount;
    private int totalViews;
    private String categoryName;
}
