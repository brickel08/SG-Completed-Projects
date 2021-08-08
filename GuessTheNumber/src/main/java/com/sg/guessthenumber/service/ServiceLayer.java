/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.service;

import com.sg.guessthenumber.models.Game;
import com.sg.guessthenumber.models.Round;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author benrickel
 */
@Component
public interface ServiceLayer {

    public Game createGame();

    public Round createRound(Round round) throws InvalidGameIdException, InvalidGuessException;

    public List<Game> getAllGames();

    public Game getGameById(int id) throws InvalidGameIdException;

    public List<Round> getRoundsByGameId(int id) throws InvalidGameIdException;

}
