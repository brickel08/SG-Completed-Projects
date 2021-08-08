/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.data;

import com.sg.guessthenumber.models.Round;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author benrickel
 */
@Repository
public class RoundDaoDB implements RoundDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Round add(Round round) {

        final String ADD_ROUND = "INSERT INTO `Round`(guess, `time`, result, gameId) VALUES(?, ?, ?, ?)";
        jdbc.update(ADD_ROUND,
                round.getGuess(),
                round.getTime(),
                round.getResult(),
                round.getGameId());
        int id = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setId(id);

        return round;

    }

    @Override
    public List<Round> getAll() {
        final String sql = "SELECT * FROM `Round`";
        return jdbc.query(sql, new RoundMapper());
    }

    @Override
    public Round findById(int id) {
        try {
            final String SELECT_ROUND_BY_ID = "SELECT * FROM `Round` WHERE id = ?";
            return jdbc.queryForObject(SELECT_ROUND_BY_ID, new RoundMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public Round update(Round round) {
        final String sql = "UPDATE `Round` SET "
                + "guess = ?, "
                + "`time` = ?, "
                + "result = ?, "
                + "gameId = ? "
                + "WHERE id = ?";
        jdbc.update(sql,
                round.getGuess(),
                round.getTime(),
                round.getResult(),
                round.getGameId(),
                round.getId());
        return round;

    }

    @Override
    @Transactional
    public boolean deleteById(int id) {
        try {
            final String DELETE_ROUND = "DELETE FROM `Round` WHERE id = ?";
            jdbc.update(DELETE_ROUND, id);
            return true;
        } catch (DataAccessException ex) {
            return false;
        }
    }

    public static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round rd = new Round();
            rd.setId(rs.getInt("id"));
            rd.setGuess(rs.getString("guess"));
            rd.setResult(rs.getString("result"));
            rd.setTime(rs.getTimestamp("time").toLocalDateTime());
            rd.setGameId(rs.getInt("gameId"));
            return rd;
        }
    }

}
