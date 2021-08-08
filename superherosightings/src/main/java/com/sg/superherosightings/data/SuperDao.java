/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.models.Super;
import java.util.List;

/**
 *
 * @author benrickel
 */
public interface SuperDao {

    Super getSuperById(int id);

    List<Super> getAllSupers();

    Super addSuper(Super supe);

    void updateSuper(Super supe);

    void deleteSuperById(int id);
    
}
