package com.sparta.schedule.domain.schedule.entity;

import com.sparta.schedule.domain.schedule.exception.PasswordNotMatched;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {
    private Long scheduleId;
    private String title;
    private String todo;
    private String userName;
    private String password;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @Builder
    public Schedule(Long scheduleId, String title,
                    String todo, String userName,
                    String password, LocalDate createdAt,
                    LocalDate updatedAt) {
        this.scheduleId = scheduleId;
        this.title = title;
        this.todo = todo;
        this.userName = userName;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void checkPassword(String password) {
        if (!this.password.equals(password)) {
            throw new PasswordNotMatched("비밀번호가 일치하지 않습니다.");
        }
    }
}