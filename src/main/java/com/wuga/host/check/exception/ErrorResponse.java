package com.wuga.host.check.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {

    private String msg;
    private HttpStatus status;

    public ErrorResponse(String msg, HttpStatus status) {
        this.msg = msg;
        this.status = status;
    }
}
