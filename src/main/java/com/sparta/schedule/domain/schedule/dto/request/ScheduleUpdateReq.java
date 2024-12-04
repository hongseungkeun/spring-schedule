package com.sparta.schedule.domain.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ScheduleUpdateReq(
        @NotBlank
        String todo
) {
}