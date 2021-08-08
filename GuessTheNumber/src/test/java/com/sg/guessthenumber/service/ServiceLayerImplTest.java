/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.service;

import com.sg.guessthenumber.TestApplicationConfiguration;
import com.sg.guessthenumber.data.GameDao;
import com.sg.guessthenumber.data.RoundDao;
import com.sg.guessthenumber.models.Game;
import com.sg.guessthenumber.models.Round;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author benrickel
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class ServiceLayerImplTest {

    @Autowired
    private ServiceLayer service;

    @Autowired
    private GameDao gameDao;

    @Autowired
    private RoundDao roundDao;

    public ServiceLayerImplTest() {
    }

    @Before
    public void setUp() {

        List<Round> rounds = roundDao.getAll();
        for (Round round : rounds) {
            roundDao.deleteById(round.getId());
        }

        List<Game> games = gameDao.getAll();
        for (Game game : games) {
            gameDao.deleteById(game.getId());
        }

    }

    /**
     * Test of createGame method, of class ServiceLayerImpl.
     */
    @Test
    public void testCreateGameGetById() throws InvalidGameIdException {

        Game game = service.createGame();

        Game fromDao = service.getGameById(game.getId());
        game.setAnswer("XXXX");

        assertEquals(game, fromDao);

    }

    /**
     * Test of createRound method, of class ServiceLayerImpl.
     */
    @Test
    public void testCreateRoundGetByGameId() throws InvalidGameIdException, InvalidGuessException {

        Game game = service.createGame();

        Round round = new Round();
        round.setGameId(game.getId());
        round.setGuess(game.getAnswer());
        round.setTime(LocalDateTime.now().withNano(0));
        service.createRound(round);

        List<Round> rounds = service.getRoundsByGameId(game.getId());

        game = service.getGameById(game.getId());

        assertTrue(rounds.contains(round));
        assertTrue(game.isFinished() == true);

    }

    /**
     * Test of getAllGames method, of class ServiceLayerImpl.
     */
    @Test
    public void testGetAllGames() {

        Game g = service.createGame();
        g.setAnswer("XXXX");

        Game h = service.createGame();
        h.setAnswer("XXXX");

        List<Game> games = service.getAllGames();

        assertEquals(2, games.size());

        assertTrue(games.get(0).equals(g));
        assertTrue(games.get(1).equals(h));

    }

    /**
     * Test of getRoundsByGameId method, of class ServiceLayerImpl.
     */
    @Test
    public void testGetRoundsByGameId() throws InvalidGameIdException {

        Game game = service.createGame();

        List<Round> rounds = service.getRoundsByGameId(game.getId());

        assertEquals(rounds, game.getRounds());

    }

    @Test
    public void testCalculatePartial() throws Exception {

        Game game = new Game();
        game = service.createGame();

        String answer = game.getAnswer();
        StringBuilder rev = new StringBuilder();
        rev.append(answer);
        rev.reverse();

        Round round = new Round();
        round.setGuess(rev.toString());
        round.setTime(LocalDateTime.now().withNano(0));
        round.setResult("e:0:p:0");
        round.setGameId(game.getId());
        round = service.createRound(round);

        assertEquals(round.getResult(), "e:0:p:4");
    }

    @Test
    public void testCalculateExact() throws Exception {

        Game game = new Game();
        game = service.createGame();

        Round round = new Round();
        round.setGuess(game.getAnswer());
        round.setTime(LocalDateTime.now().withNano(0));
        round.setResult("e:0:p:0");
        round.setGameId(game.getId());
        round = service.createRound(round);
        
        game = service.getGameById(game.getId());

        assertEquals(round.getResult(), "e:4:p:0");
        assertTrue(game.isFinished());
        assertEquals(game.getAnswer(), round.getGuess());
    }

    @Test
    public void testInvalidGuessRepeatingDigits() throws Exception {

        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(false);
        game = service.createGame();

        Round round = new Round();
        round.setGuess("1111");
        round.setTime(LocalDateTime.now().withNano(0));
        round.setResult("e:0:p:0");
        round.setGameId(game.getId());

        try {
            round = service.createRound(round);
            fail("Wrong exception thrown.");
        } catch (InvalidGuessException e) {

        } catch (InvalidGameIdException e) {
            fail("Wrong exception thrown.");
        }
    }

    @Test
    public void testInvalidGuessCharacters() throws Exception {

        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(false);
        game = service.createGame();

        Round round = new Round();
        round.setGuess("ABCD");
        round.setTime(LocalDateTime.now().withNano(0));
        round.setResult("e:0:p:0");
        round.setGameId(game.getId());

        try {
            round = service.createRound(round);
            fail("Wrong exception thrown.");
        } catch (InvalidGuessException e) {

        } catch (InvalidGameIdException e) {
            fail("Wrong exception thrown.");
        }

    }

}
