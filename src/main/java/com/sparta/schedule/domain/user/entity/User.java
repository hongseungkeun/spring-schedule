package com.sparta.schedule.domain.user.entity;

import com.sparta.schedule.domain.schedule.entity.Schedule;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    private Long id;
    private String email;
    private String password;
    private String name;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    private List<Schedule> schedules = new ArrayList<>();

    @Builder
    public User(Long id, String email,
                String password, String name,
                LocalDate createdAt, LocalDate updatedAt,
                List<Schedule> schedules) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.schedules = schedules;
    }
}