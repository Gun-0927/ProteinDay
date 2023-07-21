package com.blue.bluearchive.userpage.dto;

import lombok.Data;

@Data
public class SearchDto {
    private String searchBy;
    private String searchQuery="";
}
