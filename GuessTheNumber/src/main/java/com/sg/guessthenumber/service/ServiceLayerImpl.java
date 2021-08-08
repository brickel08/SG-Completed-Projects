/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.service;

import com.sg.guessthenumber.data.GameDaoDB;
import com.sg.guessthenumber.data.RoundDaoDB;
import com.sg.guessthenumber.models.Game;
import com.sg.guessthenumber.models.Round;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author benrickel
 */
@Service
//@Profile("service")
public class ServiceLayerImpl implements ServiceLayer {

    @Autowired
    GameDaoDB gameDao;

    @Autowired
    RoundDaoDB roundDao;

    @Override
    public Game createGame() {

        Game game = new Game();

        // generate answer
        Random r = new Random();

        Set<Integer> number = new LinkedHashSet<Integer>();
        while (number.size() < 4) {
            number.add(r.nextInt(10));
        }

        List<Integer> numbersList = new ArrayList(number);

        // setters
        game.setAnswer(numbersList.toString().replace(",", "").replace("[", "").replace("]", "").replace(" ", ""));
        game.setFinished(false);

        gameDao.add(game);

        return game;
    }

    @Override
    public Round createRound(Round round) throws InvalidGameIdException, InvalidGuessException {

        Game game = gameDao.findById(round.getGameId());

        if (validateGuess(round) == true) {
            round.setTime(LocalDateTime.now().withNano(0));
            round.setResult("e:" + exact(game, round) + ":p:" + partial(game, round));
            roundDao.add(round);

            // setting game status to true if Game = Round
            game.setFinished(setStatus(round));
        }
        gameDao.update(game);

        return round;

    }

    @Override
    public List<Game> getAllGames() {
        List<Game> games = gameDao.getAll();

        for (Game game : games) {
            if (game.isFinished() == false) {
                game.setAnswer("XXXX");
            }
        }

        return games;
    }

    @Override
    public Game getGameById(int id) throws InvalidGameIdException {

        Game game = gameDao.findById(id);

        if (game == null) {
            throw new InvalidGameIdException("Please enter a valid GameID.");
        }

        if (game.isFinished() == false) {
            game.setAnswer("XXXX");
            return game;
        }

        return game;

    }

    @Override
    public List<Round> getRoundsByGameId(int id) throws InvalidGameIdException {

        Game game = gameDao.findById(id);

        if (game == null) {
            throw new InvalidGameIdException("Please enter a valid GameID.");
        }

        if (game.isFinished() == false) {
            game.setAnswer("XXXX");
        }

        return game.getRounds();
    }

    private int exact(Game game, Round round) {

        int e = 0;

        for (int i = 0; i < round.getGuess().length(); i++) {
            if (round.getGuess().charAt(i) == (game.getAnswer().charAt(i))) {
                e++;
            }
        }
        return e;
    }

    private int partial(Game game, Round round) {

        int p = 0;

        for (int i = 0; i < round.getGuess().length(); i++) {
            if (game.getAnswer().indexOf(round.getGuess().charAt(i)) != -1
                    && round.getGuess().charAt(i) != (game.getAnswer().charAt(i))) {
                p++;

            }
        }
        return p;
    }

    private boolean setStatus(Round round) {

        Game game = gameDao.findById(round.getGameId());

        if (round.getGuess().equals(game.getAnswer())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validateGuess(Round round) throws InvalidGuessException {

        // adding Guess digits to Set to force no duplicates allowed
        boolean passValidation = true;

        Set tempSet = new HashSet();

        for (char c : round.getGuess().toCharArray()) {
            if (!tempSet.add(c)) {
                passValidation = false;
            }
        }

        if (passValidation == false) {
            throw new InvalidGuessException("Please enter a valid guess. Guesses cannot contain duplicate numbers.");
        }

        // validating Guess is only digits 0-9
        String regex = "^[0-9]+$";
        Pattern pt = Pattern.compile(regex);

        if (pt.matcher(round.getGuess()).matches() == false) {
            passValidation = false;
        }

        if (passValidation == false) {
            throw new InvalidGuessException("Please enter a valid guess. Guesses can only be 0-9.");
        }

        return passValidation;

    }
}
