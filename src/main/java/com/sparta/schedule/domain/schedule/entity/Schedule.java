package com.sparta.schedule.domain.schedule.entity;

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
}
