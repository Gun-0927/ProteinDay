package com.blue.bluearchive.admin.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminUserCareDto {
    private Long memberIdx;
    private String userCreateBy;
    private String userState;
    private LocalDateTime regTime;
    private String userName;
    private String userId;
    private String userEmail;
    private Integer accumulateReport; // 누적 신고수
    private Integer userUntreatedReport; //미처리 신고수
}
