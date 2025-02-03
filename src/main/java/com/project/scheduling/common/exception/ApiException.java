package com.project.scheduling.common.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
    private final ExceptionEnum error;
    private final HttpStatus status;

    public ApiException(ExceptionEnum error) {
        super(error.getMessage());
        this.error = error;
        this.status = error.getStatus();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ExceptionEnum getError() {
        return error;
    }
}