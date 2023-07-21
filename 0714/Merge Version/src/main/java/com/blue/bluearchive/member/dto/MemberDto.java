package com.blue.bluearchive.member.dto;

import com.blue.bluearchive.constant.Role;

import java.time.LocalDateTime;

public class MemberDto {
    private long memberIdx;

    private String memberId;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

    private String createdBy;

    private String modifiedBy;
    private String memberAddress;
    private String memberEmail;
    private String memberName;
    private String memberPassword;
    private Role role;
}
