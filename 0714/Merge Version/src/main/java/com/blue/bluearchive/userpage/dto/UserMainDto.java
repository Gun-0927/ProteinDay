package com.blue.bluearchive.userpage.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserMainDto {
    private String logTime;
    private Double userGraphWeight;
    private Double userGraphBmi;
    private Double userGraphIntakeCal;
    private Double userGraphSubtractCal;
}
