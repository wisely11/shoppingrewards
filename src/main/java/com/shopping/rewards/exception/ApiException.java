package com.shopping.rewards.exception;

import java.time.LocalDateTime;

public class ApiException {
    private final String message;
    private final String error;
    private final int status;
    private final LocalDateTime timestamp;

    public ApiException(String message, String error, int status) {
        this.message = message;
        this.error = error;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
