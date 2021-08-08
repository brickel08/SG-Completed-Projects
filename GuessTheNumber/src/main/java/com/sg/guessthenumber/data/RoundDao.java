/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.data;

import com.sg.guessthenumber.models.Round;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author benrickel
 */

public interface RoundDao {

    Round add(Round round);

    List<Round> getAll();

    Round findById(int id);

    Round update(Round round);

    boolean deleteById(int id);

}
