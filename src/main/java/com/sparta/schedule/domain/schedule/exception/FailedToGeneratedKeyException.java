package com.sparta.schedule.domain.schedule.exception;

public class FailedToGeneratedKeyException extends RuntimeException {
    public FailedToGeneratedKeyException(String message) {
        super(message);
    }
}