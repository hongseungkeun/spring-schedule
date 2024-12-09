package com.sparta.schedule.domain.schedule.dto.request;

import com.sparta.schedule.domain.schedule.dto.ScheduleValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ScheduleUpdateReq(
        @Size(max = ScheduleValidationMessages.TODO_MAX, message = ScheduleValidationMessages.TODO_MAX_MESSAGE)
        @NotBlank(message = ScheduleValidationMessages.TODO_BLANK_MESSAGE)
        String todo
) {
}