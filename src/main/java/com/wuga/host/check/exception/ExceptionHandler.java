package com.wuga.host.check.exception;

import javax.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(DoNotCreateAboveException.class)
    protected ResponseEntity<ErrorResponse> handleDoNotCreateAboveException(DoNotCreateAboveException e) {

        log.error("DoNotCreateAboveException - {}", e.getMessage());
        final ErrorResponse response = new ErrorResponse(e.getMessage(), HttpStatus.EXPECTATION_FAILED);

        return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ExistHostException.class)
    protected ResponseEntity<ErrorResponse> handleExistHostException(ExistHostException e) {

        log.error("ExistHostException - {}", e.getMessage());
        final ErrorResponse response = new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT);

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoResultException.class)
    protected ResponseEntity<ErrorResponse> handleNoResultException(NoResultException e) {

        log.error("ExistHostException - {}", e.getMessage());
        final ErrorResponse response = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
