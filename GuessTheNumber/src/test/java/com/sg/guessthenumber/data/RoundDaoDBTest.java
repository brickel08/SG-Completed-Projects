/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.data;

import com.sg.guessthenumber.models.Game;
import com.sg.guessthenumber.models.Round;
import java.time.LocalDateTime;
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
public class RoundDaoDBTest {

    @Autowired
    RoundDao roundDao;

    @Autowired
    GameDao gameDao;

    public RoundDaoDBTest() {
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
     * Test of add method, of class RoundDaoDB.
     */
    @Test
    public void testAddFindById() {

        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(false);
        gameDao.add(game);

        Round round = new Round();
        round.setGuess("1234");
        round.setTime(LocalDateTime.now().withNano(0));
        round.setResult("e:0:p:0");
        round.setGameId(game.getId());
        round = roundDao.add(round);

        Round fromDao = roundDao.findById(round.getId());

        assertEquals(round, fromDao);

    }

    /**
     * Test of getAll method, of class RoundDaoDB.
     */
    @Test
    public void testGetAll() {

        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(false);
        gameDao.add(game);

        Round round = new Round();
        round.setGuess("1234");
        round.setTime(LocalDateTime.now().withNano(0));
        round.setResult("e:0:p:0");
        round.setGameId(game.getId());
        round = roundDao.add(round);

        Round round2 = new Round();
        round2.setGuess("1234");
        round2.setTime(LocalDateTime.now().withNano(0));
        round2.setResult("e:0:p:0");
        round2.setGameId(game.getId());
        round2 = roundDao.add(round2);

        List<Round> rounds = roundDao.getAll();

        assertEquals(2, rounds.size());
        assertTrue(rounds.contains(round));
        assertTrue(rounds.contains(round2));
    }

    /**
     * Test of update method, of class RoundDaoDB.
     */
    @Test
    public void testUpdate() {

        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(false);
        gameDao.add(game);

        Round round = new Round();
        round.setGuess("1234");
        round.setTime(LocalDateTime.now().withNano(0));
        round.setResult("e:0:p:0");
        round.setGameId(game.getId());
        round = roundDao.add(round);

        Round fromDao = roundDao.findById(round.getId());

        assertEquals(round, fromDao);

        round.setGuess("4321");

        roundDao.update(round);

        assertNotEquals(round, fromDao);

        fromDao = roundDao.findById(round.getId());

        assertEquals(round, fromDao);
    }

    /**
     * Test of deleteById method, of class RoundDaoDB.
     */
    @Test
    public void testDeleteById() {

        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(false);
        gameDao.add(game);

        Round round = new Round();
        round.setGuess("1234");
        round.setTime(LocalDateTime.now().withNano(0));
        round.setResult("e:0:p:0");
        round.setGameId(game.getId());
        round = roundDao.add(round);

        List<Round> rounds = roundDao.getAll();

        assertEquals(1, rounds.size());

        gameDao.deleteById(game.getId());

        roundDao.deleteById(round.getId());

        rounds = roundDao.getAll();

        assertEquals(0, rounds.size());

    }

}
