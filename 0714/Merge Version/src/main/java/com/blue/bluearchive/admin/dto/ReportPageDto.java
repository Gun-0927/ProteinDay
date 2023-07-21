package com.blue.bluearchive.admin.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReportPageDto {
    private List<ReportDto> reportList;
    private int currentPage;
    private int totalPages;

}
