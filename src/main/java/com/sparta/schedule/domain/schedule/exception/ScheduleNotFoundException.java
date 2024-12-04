package com.sparta.schedule.domain.schedule.exception;

import com.sparta.schedule.global.exception.CustomRuntimeException;
import com.sparta.schedule.global.exception.error.ErrorCode;

public class ScheduleNotFoundException extends CustomRuntimeException {
    public ScheduleNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}