package com.sparta.schedule.domain.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ScheduleCreateReq(
        @NotBlank
        String title,

        @NotBlank
        String todo
) {
}