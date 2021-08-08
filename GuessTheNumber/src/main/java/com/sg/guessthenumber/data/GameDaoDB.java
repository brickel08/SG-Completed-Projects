/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.data;

import com.sg.guessthenumber.data.RoundDaoDB.RoundMapper;
import com.sg.guessthenumber.models.Game;
import com.sg.guessthenumber.models.Round;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class GameDaoDB implements GameDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Game add(Game game) {
        final String ADD_GAME = "INSERT INTO Game(answer, finished) VALUES(?,?)";
        jdbc.update(ADD_GAME,
                game.getAnswer(),
                game.isFinished());

        int id = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        game.setId(id);
        game.setRounds(getRoundsForGame(game));
        return game;
    }

    @Override
    public List<Game> getAll() {
        final String GET_ALL_GAMES = "SELECT * FROM Game";
        List<Game> games = jdbc.query(GET_ALL_GAMES, new GameMapper());
        addRoundsToGames(games);
        return games;
    }

    @Override
    public Game findById(int id) {
        try {
            final String FIND_BY_ID = "SELECT * FROM Game WHERE id = ?";
            Game game = jdbc.queryForObject(FIND_BY_ID, new GameMapper(), id);
            game.setRounds(getRoundsForGame(game));
            return game;
        } catch (DataAccessException ex) {
            ex.getMessage();
            return null;
        }
    }

    @Override
    @Transactional
    public Game update(Game game) {
        final String UPDATE_GAME = "UPDATE Game "
                + "SET answer = ?, finished = ? WHERE id = ?";
        jdbc.update(UPDATE_GAME,
                game.getAnswer(),
                game.isFinished(),
                game.getId());
        return game;
    }

    @Override
    public void deleteById(int id) {

        final String DELETE_ROUND = "DELETE FROM `Round` WHERE gameId = ?";
        jdbc.update(DELETE_ROUND, id);

        final String DELETE_GAME = "DELETE FROM Game WHERE id = ?";
        jdbc.update(DELETE_GAME, id);

    }

    private List<Round> getRoundsForGame(Game game) {

        final String SELECT_ROUNDS_FOR_GAME = "SELECT r.* FROM `Round` r "
                + "JOIN Game g ON r.gameId = g.id WHERE g.id = ?";

        if (jdbc.query(SELECT_ROUNDS_FOR_GAME, new RoundMapper(), game.getId()).equals(null)) {
            return new ArrayList();
        }

        return jdbc.query(SELECT_ROUNDS_FOR_GAME, new RoundMapper(), game.getId());
    }

    private void addRoundsToGames(List<Game> games) {
        for (Game game : games) {
            game.setRounds(getRoundsForGame(game));
        }
    }

    public static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game gm = new Game();
            gm.setId(rs.getInt("id"));
            gm.setAnswer(rs.getString("answer"));
            gm.setFinished(rs.getBoolean("finished"));
            return gm;
        }
    }

}
