/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.models.Location;
import com.sg.superherosightings.models.Sighting;
import com.sg.superherosightings.models.Super;
import java.util.List;

/**
 *
 * @author benrickel
 */
public interface SightingDao {

    Sighting getSightingById(int id);

    List<Sighting> getAllSightings();

    Sighting addSighting(Sighting sighting);

    void updateSighting(Sighting sighting);

    void deleteSightingById(int id);
    
    List<Sighting> getSightingsForSuper(Super supe);
    
    List<Sighting> getSightingsForLocation(Location location);
    
    List<Sighting> getAllOrderedSightings();
        
}
