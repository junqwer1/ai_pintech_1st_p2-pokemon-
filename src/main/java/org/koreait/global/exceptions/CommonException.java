package org.koreait.global.exceptions;

/*
* 모든 사용자 정의 예외 상위 클래스
*
* */

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Objects;

@Getter @Setter
public class CommonException extends RuntimeException{
    private HttpStatus status;
    private boolean errorCode;

    public CommonException(String message, HttpStatus status) {
        super(message);
        this.status = Objects.requireNonNullElse(status, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
