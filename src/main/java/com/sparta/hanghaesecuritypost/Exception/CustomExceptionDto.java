package com.sparta.hanghaesecuritypost.Exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomExceptionDto {
    private int errorCode;
    private HttpStatus httpStatus;
    private String msg;

    public CustomExceptionDto(CustomException ex) {
        this.errorCode = ex.getCustomErrorEnum().getStatus().value();
        this.httpStatus = ex.getCustomErrorEnum().getStatus();
        this.msg = ex.getCustomErrorEnum().getMessage();
    }
}
