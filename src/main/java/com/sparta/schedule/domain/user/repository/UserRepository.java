package com.sparta.schedule.domain.user.repository;

import com.sparta.schedule.domain.user.dto.request.UserSignUpReq;
import com.sparta.schedule.domain.user.entity.User;
import com.sparta.schedule.global.util.DateUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class UserRepository {
    private static final String INSERT_SQL = "INSERT INTO user(email, password, name) VALUES (?, ?, ?)";
    private static final String SELECT_SQL = "SELECT user_id, email, password, name, created_at, updated_at FROM user";
    private static final String EXIST_EMAIL_SQL = "SELECT COUNT(user_id) > 0 FROM user";
    private static final String BY_EMAIL = " WHERE email = ?";
    private static final String BY_ID = " WHERE user_id = ?";

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void save(UserSignUpReq request) {
        jdbcTemplate.update(INSERT_SQL, request.email(), request.password(), request.name());
    }

    public Optional<User> findByEmail(String email) {
        return jdbcTemplate.query(SELECT_SQL + BY_EMAIL, userRowMapper(), email).stream().findFirst();
    }

    public Optional<User> findById(Long id) {
        return jdbcTemplate.query(SELECT_SQL + BY_ID, userRowMapper(), id).stream().findFirst();
    }

    public boolean existsByEmail(String email) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(EXIST_EMAIL_SQL + BY_EMAIL, Boolean.class, email));
    }

    private RowMapper<User> userRowMapper() {
        return ((rs, rowNum) -> User.builder()
                .userId(rs.getLong("user_id"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .name(rs.getString("name"))
                .createdAt(DateUtil.convertToLocalDate(rs.getDate("created_at")))
                .updatedAt(DateUtil.convertToLocalDate(rs.getDate("updated_at")))
                .build()
        );
    }
}