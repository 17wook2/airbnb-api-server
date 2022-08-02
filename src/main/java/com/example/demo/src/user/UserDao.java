package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> getUsers() {
        String getUsersQuery = "select userId,status,latitude,longitude from user";
        return this.jdbcTemplate.query(getUsersQuery, (rs, rowNum) -> new GetUserRes(rs.getInt("userId"), rs.getInt("status"), rs.getDouble("latitude"), rs.getDouble("longitude")));
    }

    public GetUserRes getUser(int userId) {
        String getUserQuery = "select userId,status,latitude,longitude from user where userId = ?";
        int getUserParams = userId;
        return this.jdbcTemplate.queryForObject(getUserQuery, (rs, rowNum) -> new GetUserRes(rs.getInt("userId"), rs.getInt("status"), rs.getDouble("latitude"), rs.getDouble("longitude")), getUserParams);
    }

    public int checkEmail(String email) {
        String checkEmailQuery = "select exists(select userEmail from user_profile where userEmail = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery, int.class, checkEmailParams);
    }

    public int createUser(PostUserReq postUserReq) {
        String createUserQuery = "insert into user (userPassword) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(createUserQuery,new Object[]{postUserReq.getUserPassword()},keyHolder);
        int useridx = keyHolder.getKey().intValue();

        String createUserProfileQuery = "insert into user_profile (userEmail, userName, userGender, userBirthDate, userAddress, userProfileImageUrl, userPhoneNumber, host) values (?,?,?,?,?,?,?,?) where userId = ?";
        this.jdbcTemplate.update(createUserProfileQuery,new Object[]{postUserReq.getUserEmail(),postUserReq.getUserName(),postUserReq.getUserGender(),postUserReq.getUserBirthDate(),postUserReq.getUserAddress(),postUserReq.getUserProfileImageUrl(),postUserReq.getUserPhoneNumber(),postUserReq.getHost()});
        return useridx;
    }

}
