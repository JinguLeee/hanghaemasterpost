package com.sparta.hanghaesecuritypost.controller;

import com.sparta.hanghaesecuritypost.Exception.RestApiException;
import com.sparta.hanghaesecuritypost.dto.LoginRequestDto;
import com.sparta.hanghaesecuritypost.dto.SignupRequestDto;
import com.sparta.hanghaesecuritypost.security.UserDetailsImpl;
import com.sparta.hanghaesecuritypost.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "회원가입", notes = "회원가입 한다.")
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignupRequestDto signupRequestDto, BindingResult result) {
        if (result.hasErrors()){
            RestApiException restApiException = new RestApiException();
            restApiException.setHttpStatus(HttpStatus.BAD_REQUEST);
            restApiException.setErrorMessage(result.getFieldError().getDefaultMessage());

            return new ResponseEntity(
                    restApiException,
                    HttpStatus.BAD_REQUEST
            );
        }
        return userService.signup(signupRequestDto);
    }

    @ApiOperation(value = "로그인", notes = "로그인 한다.")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }

    @ApiOperation(value = "회원탈퇴", notes = "회원을 탈퇴한다.")
    @DeleteMapping("/deleteid")
    public ResponseEntity<String> deleteId(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response) {
        return userService.deleteId(userDetails.getUser(), response);
    }
}
