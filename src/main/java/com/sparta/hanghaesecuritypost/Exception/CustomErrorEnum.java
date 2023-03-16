package com.sparta.hanghaesecuritypost.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomErrorEnum {
    NOROLE(HttpStatus.UNAUTHORIZED, "권한 없숩"),
    NOPOST(HttpStatus.BAD_REQUEST, "게시글 없숨");
    private HttpStatus status;
    private String message;
}
