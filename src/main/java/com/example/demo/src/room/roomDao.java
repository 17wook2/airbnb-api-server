package com.example.demo.src.room;

import com.example.demo.src.reservation.model.GetReservationRes;
import com.example.demo.src.room.domain.Review;
import com.example.demo.src.room.domain.Room;
import com.example.demo.src.room.model.*;
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
    private final RowMapper<Room> RoomRowMapper = BeanPropertyRowMapper.newInstance(Room.class);
    private final RowMapper<GetReservationRes> getRoomReservationResRowMapper = BeanPropertyRowMapper.newInstance(GetReservationRes.class);
    private final RowMapper<GetRoomImagesRes> getRoomImagesResRowMapper = BeanPropertyRowMapper.newInstance(GetRoomImagesRes.class);
    private final RowMapper<GetRoomReviewRes> getRoomReviewResRowMapper = BeanPropertyRowMapper.newInstance(GetRoomReviewRes.class);

    public List<Room> getRooms() {
        String getRoomsQuery = "select * from room";
        return this.jdbcTemplate.query(getRoomsQuery, RoomRowMapper);
    }

    public Room getRoomById(int roomId){
        String getRoomByIdQuery = "select * from room where roomId = ?";
        return this.jdbcTemplate.queryForObject(getRoomByIdQuery,RoomRowMapper,roomId);
    }


    public List<Room> getRoomsByType(String roomType) {
        String getRoomByArchitectType = "select r.* from architectType join room_architectType raT on architectType.architectTypeId = raT.architectTypeId join room r on raT.roomId = r.roomId where architectTypeDescription = ?";
        return this.jdbcTemplate.query(getRoomByArchitectType, RoomRowMapper, roomType);
    }

    public PostRoomRes createRoom(Room room) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(room);
        long roomId = jdbcInsert.executeAndReturnKey(params).longValue();
        return new PostRoomRes(roomId);
    }

    public List<GetReservationRes> getRoomReservation(int roomId) {
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

    public Long postRoomReview(Review review) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(review);
        Long reviewId = reviewJdbcInsert.executeAndReturnKey(params).longValue();
        return reviewId;

    }
}
