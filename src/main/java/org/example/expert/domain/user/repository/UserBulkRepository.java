package org.example.expert.domain.user.repository;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.user.entity.User;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserBulkRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<User> list){

        String sql = "INSERT INTO users(email, password, user_role, nickname, image_name, created_at, modified_at)" +
                "VALUES(?,?,?,?,?,?,?)";

        jdbcTemplate.batchUpdate(sql,
                list,
                1000,
                (PreparedStatement ps, User user) ->{
                    ps.setString(1, user.getEmail());
                    ps.setString(2, user.getPassword());
                    ps.setString(3, user.getUserRole().name());
                    ps.setString(4, user.getNickname());
                    ps.setString(5, user.getImageName());
                    LocalDateTime localDateTime = LocalDateTime.now();
                    ps.setTimestamp(6, Timestamp.valueOf(localDateTime));
                    ps.setTimestamp(7, Timestamp.valueOf(localDateTime));
                });
    }
}
