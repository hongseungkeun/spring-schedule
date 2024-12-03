package com.sparta.schedule.domain.schedule.service;

import com.sparta.schedule.domain.schedule.dto.request.ScheduleCreateReq;
import com.sparta.schedule.domain.schedule.dto.request.ScheduleReadOverallReq;
import com.sparta.schedule.domain.schedule.dto.request.ScheduleUpdateReq;
import com.sparta.schedule.domain.schedule.dto.response.ScheduleReadDetailRes;
import com.sparta.schedule.domain.schedule.entity.Schedule;
import com.sparta.schedule.domain.schedule.exception.ScheduleNotFoundException;
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

    public List<ScheduleReadDetailRes> readOverallSchedule(ScheduleReadOverallReq request) {
        return scheduleRepository.findAllByUpdatedAtAndUserName(request);
    }

    public ScheduleReadDetailRes readDetailSchedule(Long scheduleId) {
        return ScheduleReadDetailRes.from(findScheduleById(scheduleId));
    }

    public void updateSchedule(Long scheduleId, ScheduleUpdateReq request) {
        Schedule schedule = findScheduleById(scheduleId);

        schedule.checkPassword(request.password());

        scheduleRepository.updateSchedule(scheduleId, request);
    }

    private Schedule findScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("일정을 찾을 수 없습니다"));
    }
}