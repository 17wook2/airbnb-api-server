package com.example.demo.src.user;


import com.example.demo.src.room.model.GetRoomRes;
import com.example.demo.src.room.model.PostRoomRes;
import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final RowMapper<GetWishlistRes> getWishlistResRowMapper = BeanPropertyRowMapper.newInstance(GetWishlistRes.class);
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
//        -- 2 Map으로 parameter 매핑
//        Map<String, Object> params2 = new HashMap<>();
//        params2.put("wishlistName",patchWishlistReq);

//        new MapSqlParameterSource()
//                .addValue("wishlistName",patchWishlistReq.getWishlistName())

    }

    public int postUserProfile(PostUserProfileReq postUserProfileReq) {
        //bean에 등록된 객체를 map객체로 만들어준다.
        SqlParameterSource params = new BeanPropertySqlParameterSource(postUserProfileReq);
        return profileJdbcInsert.executeAndReturnKey(params).intValue();
    }
}
