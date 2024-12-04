package com.sparta.schedule.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserSignUpReq(
        @Email
        @NotBlank
        String email,

        @NotBlank
        String password,

        @NotBlank
        String name
) {
}