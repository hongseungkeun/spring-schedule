package com.sparta.schedule.domain.schedule.exception;

public class PasswordNotMatched extends RuntimeException {
    public PasswordNotMatched(String message) {
        super(message);
    }
}