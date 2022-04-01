package com.Scheduled.Scheduled_server.error.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> globalException(RuntimeException e) {
        return new ResponseEntity<>(new ApiException(
                e.getMessage(),
                e,
                HttpStatus.INTERNAL_SERVER_ERROR,
                ZonedDateTime.now(ZoneId.of("Z"))),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
