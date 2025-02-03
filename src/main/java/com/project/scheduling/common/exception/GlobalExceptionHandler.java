package com.project.scheduling.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.getError().getMessage()),
                e.getStatus()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        return new ResponseEntity<>(
                new ErrorResponse(ExceptionEnum.ACCESS_DENIED.getMessage()),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
        return new ResponseEntity<>(
                new ErrorResponse(ExceptionEnum.SERVER_ERROR.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}