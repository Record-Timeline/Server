package com.api.RecordTimeline.global.exception;

public class ApiException extends RuntimeException {

    private final ErrorType errorType;

    public ApiException(final ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public ApiException(ErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}