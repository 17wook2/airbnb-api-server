package com.example.demo.src.reservation;

import com.example.demo.src.reservation.model.GetReservationRes;
import com.example.demo.src.reservation.model.PostReservationReq;
import com.example.demo.src.reservation.model.PostReservationRes;
import com.example.demo.src.room.model.PostRoomRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class reservationDao {
    private NamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("reservationId");
    }

    private final RowMapper<GetReservationRes> getReservationResRowMapper = BeanPropertyRowMapper.newInstance(GetReservationRes.class);
    private final RowMapper<PostReservationRes> postReservationResRowMapper = BeanPropertyRowMapper.newInstance(PostReservationRes.class);
    public PostReservationRes createReservation(PostReservationReq postReservationReq) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(postReservationReq);
        int reservationId = jdbcInsert.executeAndReturnKey(params).intValue();
        return new PostReservationRes(reservationId);
    }

    public List<GetReservationRes> getRoomReservationByRoomId(Long roomId) {
        String getRoomReservationByRoomIdQuery = "select * from reservation where roomId = :roomId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("roomId", roomId);
        return jdbc.query(getRoomReservationByRoomIdQuery,params,getReservationResRowMapper);
    }

    public PostReservationRes getRoomReservationByReservationId(Long reservationId) {
        String getRoomReservationByReservationIdQuery = "select * from reservation where reservationId = :reservationId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("reservationId",reservationId);
        return jdbc.queryForObject(getRoomReservationByReservationIdQuery,params,postReservationResRowMapper);
    }


    public int modifyReservation(PostReservationReq postReservationReq) {
        String updateReservationQuery = "update reservation set checkInDate = :checkInDate, checkOutDate = :checkOutDate";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(postReservationReq);
        int update = jdbc.update(updateReservationQuery, params);
        return update;
    }
}
