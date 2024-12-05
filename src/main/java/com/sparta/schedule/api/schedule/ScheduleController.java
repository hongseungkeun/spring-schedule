package com.sparta.schedule.api.schedule;

import com.sparta.schedule.domain.schedule.dto.request.ScheduleCreateReq;
import com.sparta.schedule.domain.schedule.dto.request.ScheduleUpdateReq;
import com.sparta.schedule.domain.schedule.dto.response.ScheduleReadDetailRes;
import com.sparta.schedule.domain.schedule.service.ScheduleService;
import com.sparta.schedule.global.annotation.LoginId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<Void> createSchedule(
            @RequestBody @Valid final ScheduleCreateReq request,
            @LoginId final Long userId) {

        Long scheduleId = scheduleService.createSchedule(request, userId);

        URI uri = UriComponentsBuilder.fromPath("/api/schedules/{scheduleId}").buildAndExpand(scheduleId).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<List<ScheduleReadDetailRes>> readSchedules(
            @RequestParam(required = false) final String updatedAt,
            @RequestParam(required = false) final Long id,
            @PageableDefault final Pageable pageable) {

        return ResponseEntity.ok(scheduleService.readOverallSchedule(updatedAt, id, pageable));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleReadDetailRes> readSchedule(@PathVariable final Long scheduleId) {
        return ResponseEntity.ok(scheduleService.readDetailSchedule(scheduleId));
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<Void> updateSchedule(
            @PathVariable final Long scheduleId,
            @RequestBody @Valid final ScheduleUpdateReq request,
            @LoginId final Long userId) {

        scheduleService.updateSchedule(scheduleId, request, userId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable final Long scheduleId,
            @LoginId final Long userId) {

        scheduleService.deleteSchedule(scheduleId, userId);

        return ResponseEntity.noContent().build();
    }
}