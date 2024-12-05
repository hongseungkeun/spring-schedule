package com.sparta.schedule.domain.user.exception;

import com.sparta.schedule.global.exception.CustomRuntimeException;
import com.sparta.schedule.global.exception.error.ErrorCode;

public class UserNotFoundException extends CustomRuntimeException {
    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}