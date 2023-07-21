package com.blue.bluearchive.admin.dto;

import lombok.Data;

@Data
public class AdminSearchDto {
    private String searchBy;
    private String searchQuery="";
}
