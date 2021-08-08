/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.models.Power;
import java.util.List;

/**
 *
 * @author benrickel
 */
public interface PowerDao {

    Power getPowerById(int id);

    List<Power> getAllPowers();

    Power addPower(Power power);

    void updatePower(Power power);

    void deletePowerById(int id);

}
