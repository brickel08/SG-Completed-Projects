/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.controllers;

import com.sg.guessthenumber.models.Game;
import com.sg.guessthenumber.models.Round;
import com.sg.guessthenumber.service.InvalidGameIdException;
import com.sg.guessthenumber.service.InvalidGuessException;
import com.sg.guessthenumber.service.ServiceLayer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author benrickel
 */
@RestController
@RequestMapping("/api/guessthenumber")
public class GuessTheNumberController {
    
    @Autowired
    private final ServiceLayer service;
    
    public GuessTheNumberController(ServiceLayer service) {
        this.service = service;
    }
    
        
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int createGame(@RequestBody Game game) {
        return service.createGame().getId();
    }
    
    @PostMapping("/guess")
    @ResponseStatus(HttpStatus.CREATED)
    public Round createRound(@RequestBody Round round) throws InvalidGameIdException, InvalidGuessException {
        return service.createRound(round);
    }
    
    @GetMapping("/game")
    public List<Game> getAllGames() {
        return service.getAllGames();
    }
    
    @GetMapping("/game/{id}")
    public ResponseEntity<Game> findById(@PathVariable int id) throws InvalidGameIdException {
        Game game = service.getGameById(id);
        if (game == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(game);
    }
    
    @GetMapping("/rounds/{id}")
    public ResponseEntity getRoundsByGameId(@PathVariable int id) throws InvalidGameIdException {
        List<Round> rounds = service.getRoundsByGameId(id);
         if(rounds == null) {
             return new ResponseEntity(null, HttpStatus.NOT_FOUND);
         }
         return ResponseEntity.ok(rounds);
    }

    
}
