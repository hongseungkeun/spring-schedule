package com.sparta.schedule.domain.schedule.entity;

import com.sparta.schedule.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {
    private Long scheduleId;
    private String title;
    private String todo;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    private User user;

    @Builder
    public Schedule(Long scheduleId, String title,
                    String todo, LocalDate createdAt,
                    LocalDate updatedAt, User user) {
        this.scheduleId = scheduleId;
        this.title = title;
        this.todo = todo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
    }
}