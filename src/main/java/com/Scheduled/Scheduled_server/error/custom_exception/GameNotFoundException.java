package com.Scheduled.Scheduled_server.error.custom_exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(String message) {
        super(message);
    }

    public GameNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

