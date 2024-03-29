package com.Scheduled.Scheduled_server.error.advice;

import com.Scheduled.Scheduled_server.error.advice.custom.AlreadyInLibraryException;
import com.Scheduled.Scheduled_server.error.advice.custom.GameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsException(BadCredentialsException e) {
        return new ResponseEntity<>(new ApiException(
                e.getMessage(),
                e,
                HttpStatus.FORBIDDEN,
                ZonedDateTime.now(ZoneId.of("Z"))),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = GameNotFoundException.class)
    public ResponseEntity<?> gameNotFoundException(GameNotFoundException e) {
        return new ResponseEntity<>(new ApiException(
                "game Not found",
                e,
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.of("Z"))),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AlreadyInLibraryException.class)
    public ResponseEntity<?> alreadyInLibraryException(AlreadyInLibraryException e) {
        return new ResponseEntity<>(new ApiException(
                "already there",
                e,
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))),
                HttpStatus.BAD_REQUEST);

    }
}
