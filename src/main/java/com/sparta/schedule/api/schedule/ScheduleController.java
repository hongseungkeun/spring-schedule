package com.sparta.schedule.api.schedule;

import com.sparta.schedule.domain.schedule.dto.request.ScheduleCreateReq;
import com.sparta.schedule.domain.schedule.dto.request.ScheduleGetOverallReq;
import com.sparta.schedule.domain.schedule.dto.request.ScheduleModifyReq;
import com.sparta.schedule.domain.schedule.dto.response.ScheduleGetDetailRes;
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

        URI uri = UriComponentsBuilder.fromPath("/api/schedules/{scheduleId}")
                .buildAndExpand(scheduleId)
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<List<ScheduleGetDetailRes>> readSchedules(@RequestBody final ScheduleGetOverallReq request) {
        return ResponseEntity.ok(scheduleService.getOverallSchedule(request));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleGetDetailRes> readSchedule(@PathVariable final Long scheduleId) {
        return ResponseEntity.ok(scheduleService.getDetailSchedule(scheduleId));
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<Void> updateSchedule(@PathVariable final Long scheduleId, @RequestBody final ScheduleModifyReq request) {
        scheduleService.modifySchedule(scheduleId, request);

        return ResponseEntity.ok().build();
    }
}