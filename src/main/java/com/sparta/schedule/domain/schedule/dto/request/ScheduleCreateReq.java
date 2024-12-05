package com.sparta.schedule.domain.schedule.dto.request;

import com.sparta.schedule.domain.schedule.dto.ScheduleValidationMessages;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

public record ScheduleCreateReq(
        @NotBlank(message = ScheduleValidationMessages.TITLE_BLANK_MESSAGE)
        String title,

        @Max(value = ScheduleValidationMessages.TODO_MAX, message = ScheduleValidationMessages.TODO_MAX_MESSAGE)
        @NotBlank(message = ScheduleValidationMessages.TODO_BLANK_MESSAGE)
        String todo
) {
}