package com.sparta.schedule.domain.schedule.dto.response;

import com.sparta.schedule.domain.schedule.entity.Schedule;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDate;

@Builder(access = AccessLevel.PRIVATE)
public record ScheduleReadDetailRes(
        Long scheduleId,
        String title,
        String todo,
        String name,
        LocalDate createdAt,
        LocalDate updatedAt,
        Long userId
) {
    public static ScheduleReadDetailRes from(Schedule schedule) {
        return ScheduleReadDetailRes.builder()
                .scheduleId(schedule.getScheduleId())
                .title(schedule.getTitle())
                .todo(schedule.getTodo())
                .name(schedule.getUser().getName())
                .createdAt(schedule.getCreatedAt())
                .updatedAt(schedule.getUpdatedAt())
                .userId(schedule.getUser().getUserId())
                .build();
    }
}