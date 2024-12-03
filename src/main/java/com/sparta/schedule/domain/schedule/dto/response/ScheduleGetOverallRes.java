package com.sparta.schedule.domain.schedule.dto.response;

import com.sparta.schedule.domain.schedule.entity.Schedule;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDate;

@Builder(access = AccessLevel.PRIVATE)
public record ScheduleGetOverallRes(
        Long scheduleId,
        String title,
        String todo,
        String name,
        LocalDate createdAt,
        LocalDate updatedAt
) {
    public static ScheduleGetOverallRes from(Schedule schedule) {
        return ScheduleGetOverallRes.builder()
                .scheduleId(schedule.getScheduleId())
                .title(schedule.getTitle())
                .todo(schedule.getTodo())
                .name(schedule.getUserName())
                .createdAt(schedule.getCreatedAt())
                .updatedAt(schedule.getUpdatedAt())
                .build();
    }
}