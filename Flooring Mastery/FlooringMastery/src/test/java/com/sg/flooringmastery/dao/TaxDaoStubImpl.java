/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Tax;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author benrickel
 */
public class TaxDaoStubImpl implements TaxDao {

    public Tax testTax;

    public TaxDaoStubImpl() {
        testTax = new Tax("MN");
        testTax.setStateAbbreviation("MN");
        testTax.setStateName("Minnesota");
        testTax.setTaxRate(new BigDecimal("6.875"));
    }

    @Override
    public Tax getStateTaxInfo(String stateAbbreviation) throws PersistenceException {
        if (stateAbbreviation.equals(testTax.getStateAbbreviation())) {
            return testTax;
        } else {
            return null;
        }
    }

    @Override
    public List<Tax> getAllStatesTaxInfo() throws PersistenceException {
        List<Tax> taxList = new ArrayList<>();
        taxList.add(testTax);
        return taxList;
    }

    @Override
    public boolean isValidState(String stateAbbreviation) throws PersistenceException {
        if (stateAbbreviation.contains(testTax.getStateAbbreviation())) {
            return true;
        } else {
            return false;
        }
    }
}
