package com.example.demo.src.reservation;

import com.example.demo.src.reservation.model.PostReservationReq;
import com.example.demo.src.reservation.model.PostReservationRes;
import com.example.demo.src.room.model.PostRoomRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
@RequiredArgsConstructor
public class reservationDao {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("reservationId");
    }


    public PostReservationRes createReservation(PostReservationReq postReservationReq) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(postReservationReq);
        int reservationId = jdbcInsert.executeAndReturnKey(params).intValue();
        return new PostReservationRes(reservationId);
    }
}
