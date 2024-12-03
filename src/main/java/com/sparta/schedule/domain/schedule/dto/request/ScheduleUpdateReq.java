package com.sparta.schedule.domain.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ScheduleUpdateReq(
        String todo,
        String name,

        @NotBlank
        String password
) {
}