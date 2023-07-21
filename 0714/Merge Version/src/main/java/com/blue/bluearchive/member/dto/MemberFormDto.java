package com.blue.bluearchive.member.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class MemberFormDto {
    @NotBlank(message = "이름은 필수 입력입니다.")
    private String name;

    @NotEmpty(message = "이메일은 필수 입력입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotEmpty(message = "아이디는 필수 입력입니다.")
    private String id;

    @NotEmpty(message = "비밀번호는 필수 입력입니다.")
    @Length(min=8, max=16, message = "비밀번호는 8자 이상 16자 이하로 입력해주세요.")
    private String password;

    @NotEmpty(message = "주소는 필수 입력입니다.")
    private String address;

    @NotEmpty(message = "전화번호는 필수 입력입니다.")
    @Pattern(regexp="^[0-9]{10,11}$", message="전화번호는 10자 이상 11자 이하의 숫자로 입력해주세요.")
    private String phoneNum;

    @NotEmpty(message = "별명은 필수 입력입니다.")
    private String nickName;

}

