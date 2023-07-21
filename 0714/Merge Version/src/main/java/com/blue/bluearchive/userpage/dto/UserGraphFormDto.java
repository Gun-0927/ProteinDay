package com.blue.bluearchive.userpage.dto;

import com.blue.bluearchive.member.entity.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserGraphFormDto {
    private Double userGraphWeight;
    private Double userGraphBmi;
    private Double userGraphIntakeCal;
    private Double userGraphSubtractCal;
}
