package com.sparta.schedule.domain.user.entity;

import com.sparta.schedule.domain.user.exception.LoginFailedException;
import com.sparta.schedule.global.exception.AuthFailedException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    private Long userId;
    private String email;
    private String password;
    private String name;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @Builder
    public User(Long userId, String email,
                String password, String name,
                LocalDate createdAt, LocalDate updatedAt) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void isPossibleLogin(String password) {
        if (!this.password.equals(password)) {
            throw new LoginFailedException("비밀번호가 일치하지 않습니다.");
        }
    }

    public void checkEqualId(Long id) {
        if (!this.userId.equals(id)) {
            throw new AuthFailedException("권한이 없습니다.");
        }
    }
}