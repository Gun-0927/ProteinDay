package com.blue.bluearchive.userpage.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserGraphTestDto {

    private LocalDateTime logTime;
    private Double userGraphWeight=3.3;
    private Double userGraphBmi=3.3;
    private Double userGraphIntakeCal=3.3;
    private Double userGraphSubtractCal=3.3;
}
