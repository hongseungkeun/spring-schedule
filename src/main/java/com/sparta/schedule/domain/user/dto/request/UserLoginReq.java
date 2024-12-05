package com.sparta.schedule.domain.user.dto.request;

import com.sparta.schedule.domain.user.dto.UserValidationMessages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserLoginReq(
        @Pattern(regexp = UserValidationMessages.EMAIL_PATTERN_REG, message = UserValidationMessages.EMAIL_PATTERN_MESSAGE)
        @NotBlank(message = UserValidationMessages.EMAIL_BLANK_MESSAGE)
        String email,

        @Min(value = UserValidationMessages.PASSWORD_MIN, message = UserValidationMessages.PASSWORD_MIN_MESSAGE)
        @NotBlank(message = UserValidationMessages.PASSWORD_BLANK_MESSAGE)
        String password
) {
}
