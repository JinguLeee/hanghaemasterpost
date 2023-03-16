package com.sparta.hanghaesecuritypost.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public ResponseEntity<Object> handleApiRequestException(IllegalArgumentException ex) {
        RestApiException restApiException = new RestApiException();
        restApiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        restApiException.setErrorMessage(ex.getMessage());

        return new ResponseEntity(
                restApiException,
                HttpStatus.BAD_REQUEST
        );
    }

    // TODO : 커스텀 한 예외처리
    @ExceptionHandler(value = { CustomException.class })
    public ResponseEntity<Object>  CustomhandleApiRequestException(CustomException ex) {
        // ExceptionDto는 화면에 보여줄 내용을 정의
        return ResponseEntity.status(ex.getCustomErrorEnum().getStatus()).body(new CustomExceptionDto(ex));
    }
}