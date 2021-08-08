/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Tax;
import java.util.List;

/**
 *
 * @author benrickel
 */
public interface TaxDao {

    Tax getStateTaxInfo(String stateAbbreviation) throws PersistenceException;

    List<Tax> getAllStatesTaxInfo() throws PersistenceException;

    boolean isValidState(String stateAbbreviation) throws PersistenceException;

}
