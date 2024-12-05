package com.sparta.schedule.global.exception;

import com.sparta.schedule.global.exception.error.ErrorCode;

public class AuthFailedException extends CustomRuntimeException {
    public AuthFailedException(ErrorCode errorCode) {
        super(errorCode);
    }
}