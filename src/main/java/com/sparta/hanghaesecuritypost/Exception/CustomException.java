package com.sparta.hanghaesecuritypost.Exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private CustomErrorEnum customErrorEnum;

    public CustomException(CustomErrorEnum customErrorEnum) {
        this.customErrorEnum = customErrorEnum;
    }
}
