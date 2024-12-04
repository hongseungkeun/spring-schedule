package com.sparta.schedule.domain.user.exception;

import com.sparta.schedule.global.exception.CustomRuntimeException;
import com.sparta.schedule.global.exception.error.ErrorCode;

public class AlreadyExistUserException extends CustomRuntimeException {
    public AlreadyExistUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}