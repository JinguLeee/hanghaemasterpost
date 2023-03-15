package com.sparta.hanghaesecuritypost.dto;

import com.sparta.hanghaesecuritypost.entity.UserRoleEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
public class SignupRequestDto {
    @NotBlank(message = "아이디 필수 입력값입니다.")   // Null, "", 공백 포함 빈값체크
    @Size(max = 10, min = 4, message = "아이디 자릿수는 4~10로 입력")
    @Pattern(regexp = "^[a-z\\d]*$", message= "아이디는 소문자와 숫자만 사용 가능")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(max = 15, min = 8, message = "비밀번호 자릿수는 4~10로 입력")
    @Pattern(regexp = "^[A-Za-z\\d@$!%*?&]*$", message= "비밀번호는 대소문자와 숫자, 특수문자만 사용 가능")
    private String password;

    private UserRoleEnum role;
}