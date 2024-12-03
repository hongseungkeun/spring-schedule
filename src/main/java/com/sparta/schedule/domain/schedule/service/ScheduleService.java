package com.sparta.schedule.domain.schedule.service;

import com.sparta.schedule.domain.schedule.dto.request.ScheduleCreateReq;
import com.sparta.schedule.domain.schedule.dto.request.ScheduleGetOverallReq;
import com.sparta.schedule.domain.schedule.dto.response.ScheduleGetOverallRes;
import com.sparta.schedule.domain.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public Long createSchedule(ScheduleCreateReq request) {
        return scheduleRepository.save(request);
    }

    public List<ScheduleGetOverallRes> getOverallSchedule(ScheduleGetOverallReq request) {
        return scheduleRepository.findAllByUpdatedAtAndUserName(request);
    }
}