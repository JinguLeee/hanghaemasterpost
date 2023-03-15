package com.sparta.hanghaesecuritypost.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ResultResponseDto {
    private HttpStatus status;
    private int statusCode;
    private String message;

    public ResultResponseDto(HttpStatus status, int statusCode, String message) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
    }
}