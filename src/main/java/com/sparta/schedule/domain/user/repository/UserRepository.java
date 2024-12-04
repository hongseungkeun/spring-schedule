package com.sparta.schedule.domain.user.repository;

import com.sparta.schedule.domain.user.dto.UserSignUpReq;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserRepository {
    private static final String INSERT_SQL = "INSERT INTO user(email, password, name) VALUES (?, ?, ?)";
    private static final String BY_ID = " WHERE user_Id = ?";

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void save(UserSignUpReq request) {
        jdbcTemplate.update(INSERT_SQL, request.email(), request.password(), request.name());
    }
}