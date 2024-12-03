package com.sparta.schedule.domain.schedule.repository;

import com.sparta.schedule.domain.schedule.dto.request.ScheduleCreateReq;
import com.sparta.schedule.domain.schedule.dto.request.ScheduleReadOverallReq;
import com.sparta.schedule.domain.schedule.dto.request.ScheduleUpdateReq;
import com.sparta.schedule.domain.schedule.dto.response.ScheduleReadDetailRes;
import com.sparta.schedule.domain.schedule.entity.Schedule;
import com.sparta.schedule.domain.schedule.exception.FailedToGeneratedKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long save(ScheduleCreateReq request) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO SCHEDULE(title, todo, user_name, password) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, new String[]{"scheduleId"});
            ps.setString(1, request.title());
            ps.setString(2, request.todo());
            ps.setString(3, request.name());
            ps.setString(4, request.password());
            return ps;
        }, keyHolder);

        return Optional.ofNullable(keyHolder.getKey())
                .map(Number::longValue)
                .orElseThrow(() -> new FailedToGeneratedKeyException("생성된 키를 검색하지 못했습니다."));
    }

    public List<ScheduleReadDetailRes> findAllByUpdatedAtAndUserName(ScheduleReadOverallReq request) {
        List<String> queryArgs = new ArrayList<>();
        String sql = "SELECT schedule_id, title, todo, user_name, password, createdAt, updatedAt FROM SCHEDULE";

        if (StringUtils.hasText(request.updatedAt()) || StringUtils.hasText(request.name())) {
            sql += " WHERE ";
        }

        boolean orFlag = false;

        if (StringUtils.hasText(request.updatedAt())) {
            sql += "? <= updatedAt";
            queryArgs.add(request.updatedAt());
            orFlag = true;
        }

        if (StringUtils.hasText(request.name())) {
            if (orFlag) {
                sql += " OR ";
            }

            sql += "user_name = ?";
            queryArgs.add(request.name());
        }

        sql += " ORDER BY updatedAt DESC";

        return jdbcTemplate.query(sql, scheduleRowMapper(), queryArgs.toArray()).stream()
                .map(ScheduleReadDetailRes::from)
                .toList();
    }

    public void updateSchedule(Long scheduleId, ScheduleUpdateReq request) {
        String sql = "UPDATE schedule set todo = ?, user_name = ? WHERE schedule_id = ?";

        jdbcTemplate.update(sql, request.todo(), request.name(), scheduleId);
    }

    public Optional<Schedule> findById(Long scheduleId) {
        String sql = "SELECT schedule_id, title, todo, user_name, password, createdAt, updatedAt FROM SCHEDULE";
        sql += " WHERE schedule_id = ?";

        return jdbcTemplate.query(sql, scheduleRowMapper(), scheduleId).stream()
                .findFirst();
    }

    private RowMapper<Schedule> scheduleRowMapper() {
        return ((rs, rowNum) -> Schedule.builder()
                .scheduleId(rs.getLong("schedule_id"))
                .title(rs.getString("title"))
                .todo(rs.getString("todo"))
                .userName(rs.getString("user_name"))
                .password(rs.getString("password"))
                .createdAt(convertToLocalDate(rs.getDate("createdAt")))
                .updatedAt(convertToLocalDate(rs.getDate("updatedAt")))
                .build()
        );
    }

    private LocalDate convertToLocalDate(Date date) {
        return date.toLocalDate();
    }
}