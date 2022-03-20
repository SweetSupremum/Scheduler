package com.Scheduled.Scheduled_server.error.advice;

import com.Scheduled.Scheduled_server.error.custom_exception.GameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = GameNotFoundException.class)
    public ResponseEntity<Object> gameNotFound(GameNotFoundException e) {
        return new ResponseEntity<>(new ApiException("Game not found",
                e,
                HttpStatus.INTERNAL_SERVER_ERROR,
                ZonedDateTime.now(ZoneId.of("Z"))),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> globalException(Exception e) {
        return new ResponseEntity<>(new ApiException(e.getMessage(),
                e,
                HttpStatus.INTERNAL_SERVER_ERROR,
                ZonedDateTime.now(ZoneId.of("Z"))),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}