package com.sparta.schedule.domain.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ScheduleDeleteReq(
        @NotBlank
        String password
) {
}