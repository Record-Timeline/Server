package com.api.RecordTimeline.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Map<String, Object>> handleApiException(ApiException e) {
        ErrorType errorType = e.getErrorType();
        Map<String, Object> response = new HashMap<>();
        response.put("status", errorType.getStatus().value());
        response.put("errorCode", errorType.getErrorCode());
        response.put("message", errorType.getMessage());

        return ResponseEntity
                .status(errorType.getStatus())
                .body(response);
    }
}


