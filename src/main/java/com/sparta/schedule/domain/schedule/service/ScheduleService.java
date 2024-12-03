package com.sparta.schedule.domain.schedule.service;

import com.sparta.schedule.domain.schedule.dto.request.ScheduleCreateReq;
import com.sparta.schedule.domain.schedule.dto.request.ScheduleGetOverallReq;
import com.sparta.schedule.domain.schedule.dto.request.ScheduleModifyReq;
import com.sparta.schedule.domain.schedule.dto.response.ScheduleGetDetailRes;
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

    public List<ScheduleGetDetailRes> getOverallSchedule(ScheduleGetOverallReq request) {
        return scheduleRepository.findAllByUpdatedAtAndUserName(request);
    }

    public ScheduleGetDetailRes getDetailSchedule(Long scheduleId) {
        return ScheduleGetDetailRes.from(findScheduleById(scheduleId));
    }

    public void modifySchedule(Long scheduleId, ScheduleModifyReq request) {
        Schedule schedule = findScheduleById(scheduleId);

        schedule.checkPassword(request.password());

        scheduleRepository.modifySchedule(scheduleId, request);
    }

    private Schedule findScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("일정을 찾을 수 없습니다"));
    }
}