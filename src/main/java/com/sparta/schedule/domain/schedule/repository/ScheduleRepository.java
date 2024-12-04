package com.sparta.schedule.domain.schedule.repository;

import com.sparta.schedule.domain.schedule.dto.request.ScheduleCreateReq;
import com.sparta.schedule.domain.schedule.dto.request.ScheduleUpdateReq;
import com.sparta.schedule.domain.schedule.dto.response.ScheduleReadDetailRes;
import com.sparta.schedule.domain.schedule.entity.Schedule;
import com.sparta.schedule.domain.schedule.exception.FailedToGeneratedKeyException;
import com.sparta.schedule.global.util.DateUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ScheduleRepository {
    private static final String INSERT_SQL = "INSERT INTO schedule(title, todo, user_name, password) VALUES (?, ?, ?, ?)";
    private static final String SELECT_SQL = "SELECT schedule_id, title, todo, user_name, password, createdAt, updatedAt FROM schedule";
    private static final String UPDATE_SQL = "UPDATE schedule set todo = ?, user_name = ?";
    private static final String DELETE_SQL = "DELETE FROM schedule";
    private static final String BY_ID = " WHERE schedule_id = ?";
    private static final String ORDER_BY_UPDATED_AT_DESC = " ORDER BY updatedAt DESC";
    private static final String WHERE = " WHERE ";
    private static final String OR = " OR ";
    private static final String CONDITION_UPDATED_AT = "? <= updatedAt";
    private static final String CONDITION_USER_NAME = "user_name = ?";

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long save(ScheduleCreateReq request) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(INSERT_SQL, new String[]{"scheduleId"});
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

    public List<ScheduleReadDetailRes> findAllByUpdatedAtAndUserName(String updatedAt, String name) {
        StringBuilder queryBuilder = new StringBuilder(SELECT_SQL);
        List<String> queryArgs = new ArrayList<>();

        if (StringUtils.hasText(updatedAt) || StringUtils.hasText(name)) {
            queryBuilder.append(WHERE);
        }

        boolean orFlag = false;

        if (StringUtils.hasText(updatedAt)) {
            queryBuilder.append(CONDITION_UPDATED_AT);
            queryArgs.add(updatedAt);
            orFlag = true;
        }

        if (StringUtils.hasText(name)) {
            if (orFlag) {
                queryBuilder.append(OR);
            }

            queryBuilder.append(CONDITION_USER_NAME);
            queryArgs.add(name);
        }

        queryBuilder.append(ORDER_BY_UPDATED_AT_DESC);

        return jdbcTemplate.query(queryBuilder.toString(), scheduleRowMapper(), queryArgs.toArray()).stream()
                .map(ScheduleReadDetailRes::from)
                .toList();
    }

    public void update(Long scheduleId, ScheduleUpdateReq request) {
        jdbcTemplate.update(UPDATE_SQL + BY_ID, request.todo(), request.name(), scheduleId);
    }

    public void delete(Long scheduleId) {
        jdbcTemplate.update(DELETE_SQL + BY_ID, scheduleId);
    }

    public Optional<Schedule> findById(Long scheduleId) {
        return jdbcTemplate.query(SELECT_SQL + BY_ID, scheduleRowMapper(), scheduleId).stream().findFirst();
    }

    private RowMapper<Schedule> scheduleRowMapper() {
        return ((rs, rowNum) -> Schedule.builder()
                .scheduleId(rs.getLong("schedule_id"))
                .title(rs.getString("title"))
                .todo(rs.getString("todo"))
                .createdAt(DateUtil.convertToLocalDate(rs.getDate("createdAt")))
                .updatedAt(DateUtil.convertToLocalDate(rs.getDate("updatedAt")))
                .build()
        );
    }
}