package com.Scheduled.Scheduled_server.error.advice;

import com.Scheduled.Scheduled_server.error.custom_exception.GameNotFoundException;
import com.Scheduled.Scheduled_server.error.custom_exception.ParserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = GameNotFoundException.class)
    public ResponseEntity<?> gameNotFound(GameNotFoundException e) {
        return new ResponseEntity<>(new ApiException("Game not found",
                e,
                HttpStatus.INTERNAL_SERVER_ERROR,
                ZonedDateTime.now(ZoneId.of("Z"))),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> globalException(Exception e) {
        return new ResponseEntity<>(new ApiException(e.getMessage(),
                e,
                HttpStatus.INTERNAL_SERVER_ERROR,
                ZonedDateTime.now(ZoneId.of("Z"))),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = ParserException.class)
    public ResponseEntity<?> parserException(ParserException e) {
        return new ResponseEntity<>(new ApiException("parser error",
                e,
                HttpStatus.INTERNAL_SERVER_ERROR,
                ZonedDateTime.now(ZoneId.of("Z"))), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = IOException.class)
    public ResponseEntity<?> connectException(IOException e) {
        return new ResponseEntity<>(new ApiException("connect error",
                e,
                HttpStatus.INTERNAL_SERVER_ERROR,
                ZonedDateTime.now(ZoneId.of("Z"))), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
