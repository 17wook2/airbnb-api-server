package com.example.demo.src.room;

import com.example.demo.src.room.model.GetRoomRes;
import com.example.demo.src.room.model.PostRoomReq;
import com.example.demo.src.room.model.PostRoomRes;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class roomDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("room").usingGeneratedKeyColumns("roomId");
    }
    private final RowMapper<GetRoomRes> getRoomRowMapper = BeanPropertyRowMapper.newInstance(GetRoomRes.class);
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
        SqlParameterSource query = new BeanPropertySqlParameterSource(postRoomReq);
        long roomId = jdbcInsert.executeAndReturnKey(query).longValue();
        return new PostRoomRes(roomId);
    }
}
