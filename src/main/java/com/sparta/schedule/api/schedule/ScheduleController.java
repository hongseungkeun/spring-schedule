package com.sparta.schedule.api.schedule;

import com.sparta.schedule.domain.schedule.dto.request.ScheduleCreateReq;
import com.sparta.schedule.domain.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid final ScheduleCreateReq request) {
        Long scheduleId = scheduleService.createSchedule(request);

        URI uri = UriComponentsBuilder.fromPath("/api/schedules/{scheduleId}")
                .buildAndExpand(scheduleId)
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
