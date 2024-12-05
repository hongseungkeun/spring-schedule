package com.sparta.schedule.domain.user.exception;

import com.sparta.schedule.global.exception.CustomRuntimeException;
import com.sparta.schedule.global.exception.error.ErrorCode;

public class LoginFailedException extends CustomRuntimeException {
    public LoginFailedException(ErrorCode errorCode) {
        super(errorCode);
    }
}