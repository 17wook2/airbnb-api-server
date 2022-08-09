package com.example.demo.src.room;

import com.example.demo.src.room.model.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class roomDao {

    private NamedParameterJdbcTemplate jdbc;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private SimpleJdbcInsert reviewJdbcInsert;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("room").usingGeneratedKeyColumns("roomId");
        this.reviewJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("review").usingGeneratedKeyColumns("reviewId");
    }
    private final RowMapper<GetRoomRes> getRoomRowMapper = BeanPropertyRowMapper.newInstance(GetRoomRes.class);
    private final RowMapper<GetRoomReservationRes> getRoomReservationResRowMapper = BeanPropertyRowMapper.newInstance(GetRoomReservationRes.class);

    private final RowMapper<GetRoomImagesRes> getRoomImagesResRowMapper = BeanPropertyRowMapper.newInstance(GetRoomImagesRes.class);
    private final RowMapper<GetRoomReviewRes> getRoomReviewResRowMapper = BeanPropertyRowMapper.newInstance(GetRoomReviewRes.class);

    public List<GetRoomRes> getRooms() {
        String getRoomsQuery = "select * from room";
        return this.jdbcTemplate.query(getRoomsQuery, getRoomRowMapper);
    }

    public GetRoomRes getRoomById(int roomId){
        String getRoomByIdQuery = "select * from room where roomId = ?";
        return this.jdbcTemplate.queryForObject(getRoomByIdQuery,getRoomRowMapper,roomId);
    }


    public List<GetRoomRes> getRoomsByType(String roomType) {
        String getRoomByArchitectType = "select r.* from architectType join room_architectType raT on architectType.architectTypeId = raT.architectTypeId join room r on raT.roomId = r.roomId where architectTypeDescription = ?";
        return this.jdbcTemplate.query(getRoomByArchitectType, getRoomRowMapper, roomType);
    }

    public PostRoomRes createRoom(PostRoomReq postRoomReq) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(postRoomReq);
        long roomId = jdbcInsert.executeAndReturnKey(params).longValue();
        return new PostRoomRes(roomId);
    }

    public List<GetRoomReservationRes> getRoomReservation(int roomId) {
        String getRoomReservationQuery = "select * from reservation where roomId = ?";
        return this.jdbcTemplate.query(getRoomReservationQuery,getRoomReservationResRowMapper,roomId);
    }


    public List<GetRoomImagesRes> getRoomImages(int roomId) {
        String getRoomImagesQuery = "select * from room_image where roomId = :roomId";
        HashMap<String, Integer> params = new HashMap<>();
        params.put("roomId",roomId);
        return jdbc.query(getRoomImagesQuery,params,getRoomImagesResRowMapper);
    }

    public List<GetRoomReviewRes> getRoomReview(int roomId) {
        String getRoomReviewQuery = "select * from review where roomId = :roomId";
        HashMap<String, Integer> params = new HashMap<>();
        params.put("roomId",roomId);
        return jdbc.query(getRoomReviewQuery,params,getRoomReviewResRowMapper);

    }

    public int postRoomReview(PostRoomReviewReq postRoomReviewReq) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(postRoomReviewReq);
        int reviewId = jdbcInsert.executeAndReturnKey(params).intValue();
        return reviewId;

    }
}
