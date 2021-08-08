/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.data;

import com.sg.guessthenumber.models.Game;
import com.sg.guessthenumber.models.Round;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author benrickel
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GameDaoDBTest {

    @Autowired
    GameDao gameDao;

    @Autowired
    RoundDao roundDao;

    public GameDaoDBTest() {
    }

    @Before
    public void setUp() {

        List<Game> games = gameDao.getAll();
        for (Game game : games) {
            gameDao.deleteById(game.getId());
        }

        List<Round> rounds = roundDao.getAll();
        for (Round round : rounds) {
            roundDao.deleteById(round.getId());
        }

    }

    /**
     * Test of add method, of class GameDaoDB.
     */
    @Test
    public void testAddFindById() {

        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(true);
        game = gameDao.add(game);

        Game fromDao = gameDao.findById(game.getId());

        assertEquals(game, fromDao);
    }

    /**
     * Test of getAll method, of class GameDaoDB.
     */
    @Test
    public void testGetAll() {

        Round round = new Round();
        round.setGuess("1234");
        round.setTime(LocalDateTime.now().withNano(0));
        round.setResult("e:0:p:0");

        List<Round> rounds = new ArrayList();
        rounds.add(round);

        Game game = new Game();
        game.setRounds(rounds);
        game.setAnswer("1234");
        game.setFinished(true);
        game = gameDao.add(game);

        Game game2 = new Game();
        game2.setRounds(rounds);
        game2.setAnswer("1234");
        game2.setFinished(true);
        game2 = gameDao.add(game2);

        List<Game> games = gameDao.getAll();

        assertEquals(2, games.size());
        assertTrue(games.contains(game));
        assertTrue(games.contains(game2));
    }

    /**
     * Test of update method, of class GameDaoDB.
     */
    @Test
    public void testUpdate() {

        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(true);
        game = gameDao.add(game);

        Game fromDao = gameDao.findById(game.getId());

        assertEquals(game, fromDao);

        game.setAnswer("4321");

        gameDao.update(game);

        assertNotEquals(game, fromDao);

        fromDao = gameDao.findById(game.getId());

        assertEquals(game, fromDao);
    }

    /**
     * Test of deleteById method, of class GameDaoDB.
     */
    @Test
    public void testDeleteById() {

        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(false);
        game = gameDao.add(game);

        List<Game> games = gameDao.getAll();

        assertEquals(1, games.size());

        gameDao.deleteById(game.getId());

        games = gameDao.getAll();

        assertEquals(0, games.size());
    }

}
