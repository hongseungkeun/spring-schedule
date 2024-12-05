package com.sparta.schedule.domain.schedule.dto.request;

import com.sparta.schedule.domain.schedule.dto.ScheduleValidationMessages;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

public record ScheduleUpdateReq(
        @Max(value = ScheduleValidationMessages.TODO_MAX, message = ScheduleValidationMessages.TODO_MAX_MESSAGE)
        @NotBlank(message = ScheduleValidationMessages.TODO_BLANK_MESSAGE)
        String todo
) {
}