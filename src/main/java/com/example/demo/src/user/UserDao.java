package com.example.demo.src.user;


import com.example.demo.src.room.model.GetRoomRes;
import com.example.demo.src.room.model.PostRoomRes;
import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDao {

    private NamedParameterJdbcTemplate jdbc;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert wishlistJdbcInsert;
    private SimpleJdbcInsert profileJdbcInsert;

    private SimpleJdbcInsert userJdbcInsert;
    private final RowMapper<GetWishlistRes> getWishlistResRowMapper = BeanPropertyRowMapper.newInstance(GetWishlistRes.class);
    private final RowMapper<GetUserRes> getUserResRowMapper = BeanPropertyRowMapper.newInstance(GetUserRes.class);
    private final RowMapper<GetUserReviewRes> getUserReviewResRowMapper = BeanPropertyRowMapper.newInstance(GetUserReviewRes.class);
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.wishlistJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("wishlists")
                .usingGeneratedKeyColumns("wishlistId");
        this.profileJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("user_profile")
                .usingGeneratedKeyColumns("userProfileId");
        this.userJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("user")
                .usingGeneratedKeyColumns("userId");
    }

    public List<GetUserRes> getUsers() {
        String getUsersQuery = "select * from user inner join user_profile on user.userId = user_profile.userId";
        return jdbc.query(getUsersQuery,getUserResRowMapper);
    }

    public GetUserRes getUser(int userId) {
        String getUserQuery = "select * from user inner join user_profile on user.userId = user_profile.userId where user.userId = :userId";
        MapSqlParameterSource namedParameter = new MapSqlParameterSource("userId", userId);
        return jdbc.queryForObject(getUserQuery,namedParameter,getUserResRowMapper);
    }

    public int checkEmail(String email) {
        String checkEmailQuery = "select exists(select userEmail from user_profile where userEmail = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery, int.class, checkEmailParams);
    }

    public int createUser(PostUserReq postUserReq) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(postUserReq);
        int userIdx = userJdbcInsert.executeAndReturnKey(params).intValue();
        return userIdx;
    }


    public List<GetWishlistRes> getWishlists(int userId) {
        String getWishlistsQuery = "select * from wishlists where userId = ?";
        return jdbcTemplate.query(getWishlistsQuery,getWishlistResRowMapper,userId);
    }

    public int createWishlist(PostWishlistReq postWishlistReq) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(postWishlistReq);
        return wishlistJdbcInsert.executeAndReturnKey(params).intValue();
    }

    public int modifyWishlist(PatchWishlistReq patchWishlistReq) {
        String modifyWishlistQuery = "update wishlists set wishlistName = :wishlistName where wishlistId = :wishlistId";
        SqlParameterSource params = new BeanPropertySqlParameterSource(patchWishlistReq);
        return jdbc.update(modifyWishlistQuery,params);
    }

    public int postUserProfile(PostUserProfileReq postUserProfileReq) {
        //bean에 등록된 객체를 map객체로 만들어준다.
        SqlParameterSource params = new BeanPropertySqlParameterSource(postUserProfileReq);
        return profileJdbcInsert.executeAndReturnKey(params).intValue();
    }

    public List<GetUserReviewRes> getReviews(int userId) {
        String getReviewsQuery = "select * from review where guestId = :userId";
        HashMap<String, Integer> params = new HashMap<>();
        params.put("userId",userId);
        return jdbc.query(getReviewsQuery,params,getUserReviewResRowMapper);
    }
}
