package com.sparta.schedule.domain.schedule.exception;

import com.sparta.schedule.global.exception.CustomRuntimeException;
import com.sparta.schedule.global.exception.error.ErrorCode;

public class FailedToGeneratedKeyException extends CustomRuntimeException {
    public FailedToGeneratedKeyException(ErrorCode errorCode) {
        super(errorCode);
    }
}