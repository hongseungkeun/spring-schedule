package com.sparta.schedule.domain.user.exception;

public class AlreadyExistUserException extends RuntimeException {
    public AlreadyExistUserException(String message) {
        super(message);
    }
}