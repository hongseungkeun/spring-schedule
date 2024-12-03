package com.sparta.schedule.domain.schedule.exception;

public class FailedToGeneratedKey extends RuntimeException {
    public FailedToGeneratedKey(String message) {
        super(message);
    }
}