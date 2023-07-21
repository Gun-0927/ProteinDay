package com.blue.bluearchive.admin.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DailyData {
    private LocalDate date;
    private int views;
    private int boardCount;
}
