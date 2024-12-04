package com.sparta.schedule.domain.schedule.service;

import com.sparta.schedule.domain.schedule.dto.request.ScheduleCreateReq;
import com.sparta.schedule.domain.schedule.dto.request.ScheduleUpdateReq;
import com.sparta.schedule.domain.schedule.dto.response.ScheduleReadDetailRes;
import com.sparta.schedule.domain.schedule.entity.Schedule;
import com.sparta.schedule.domain.schedule.exception.ScheduleNotFoundException;
import com.sparta.schedule.domain.schedule.repository.ScheduleRepository;
import com.sparta.schedule.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final UserService userService;
    private final ScheduleRepository scheduleRepository;

    public Long createSchedule(ScheduleCreateReq request, Long userId) {
        return scheduleRepository.save(request, userId);
    }

    public List<ScheduleReadDetailRes> readOverallSchedule(String updatedAt, Long id) {
        return scheduleRepository.findAllByUpdatedAtAndUserName(updatedAt, id);
    }

    public ScheduleReadDetailRes readDetailSchedule(Long scheduleId) {
        return ScheduleReadDetailRes.from(findScheduleById(scheduleId));
    }

    public void updateSchedule(Long scheduleId, ScheduleUpdateReq request, Long userId) {
        checkEqualUserId(scheduleId, userId);

        scheduleRepository.update(scheduleId, request);
    }

    public void deleteSchedule(Long scheduleId, Long userId) {
        checkEqualUserId(scheduleId, userId);

        scheduleRepository.delete(scheduleId);
    }

    private Schedule findScheduleById(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("일정을 찾을 수 없습니다"));

        schedule.setUser(userService.findUserById(schedule.getUser().getUserId()));

        return schedule;
    }

    private void checkEqualUserId(Long scheduleId, Long userId) {
        Schedule schedule = findScheduleById(scheduleId);

        schedule.getUser().checkEqualId(userId);
    }
}