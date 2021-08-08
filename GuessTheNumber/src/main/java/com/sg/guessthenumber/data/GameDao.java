/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.data;

import com.sg.guessthenumber.models.Game;
import java.util.List;

/**
 *
 * @author benrickel
 */
public interface GameDao {

    public Game add(Game game);

    public List<Game> getAll();

    public Game findById(int id);

    public Game update(Game game);

    public void deleteById(int id);

}
