package com.sparta.schedule.api.schedule;

import com.sparta.schedule.domain.schedule.dto.request.ScheduleCreateReq;
import com.sparta.schedule.domain.schedule.dto.request.ScheduleDeleteReq;
import com.sparta.schedule.domain.schedule.dto.request.ScheduleReadOverallReq;
import com.sparta.schedule.domain.schedule.dto.request.ScheduleUpdateReq;
import com.sparta.schedule.domain.schedule.dto.response.ScheduleReadDetailRes;
import com.sparta.schedule.domain.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Void> createSchedule(@RequestBody @Valid final ScheduleCreateReq request) {
        Long scheduleId = scheduleService.createSchedule(request);

        URI uri = UriComponentsBuilder.fromPath("/api/schedules/{scheduleId}").buildAndExpand(scheduleId).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<List<ScheduleReadDetailRes>> readSchedules(@RequestBody final ScheduleReadOverallReq request) {
        return ResponseEntity.ok(scheduleService.readOverallSchedule(request));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleReadDetailRes> readSchedule(@PathVariable final Long scheduleId) {
        return ResponseEntity.ok(scheduleService.readDetailSchedule(scheduleId));
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<Void> updateSchedule(@PathVariable final Long scheduleId, @RequestBody final ScheduleUpdateReq request) {
        scheduleService.updateSchedule(scheduleId, request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable final Long scheduleId, @RequestBody final ScheduleDeleteReq request) {
        scheduleService.deleteSchedule(scheduleId, request);

        return ResponseEntity.noContent().build();
    }
}