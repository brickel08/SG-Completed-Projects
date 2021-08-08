/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Tax;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author benrickel
 */
public class TaxDaoFileImplTest {

    TaxDao testDao;

    public TaxDaoFileImplTest() {
    }

    @BeforeEach
    public void setUp() throws Exception {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testDao = ctx.getBean("taxDao", TaxDaoFileImpl.class);

    }

    @Test
    public void testGetStateTaxInfo() throws Exception {

        Tax tax = new Tax("MN");
        tax.setStateName("Minnesota");
        tax.setTaxRate(new BigDecimal("6.875"));

        Tax retrievedTax = testDao.getStateTaxInfo("MN");

        assertEquals(tax.getStateAbbreviation(), retrievedTax.getStateAbbreviation(), "Checking state abbreviation.");
        assertEquals(tax.getStateName(), retrievedTax.getStateName(), "Checking state name.");
        assertEquals(tax.getTaxRate(), retrievedTax.getTaxRate(), "Checking state tax rate.");

    }

    @Test
    public void testGetAllStatesTaxInfo() throws PersistenceException, FileNotFoundException {
        Tax tax = new Tax("MN");
        tax.setStateName("Minnesota");
        tax.setTaxRate(new BigDecimal("6.875"));

        Tax tax2 = new Tax("CA");
        tax2.setStateName("California");
        tax2.setTaxRate(new BigDecimal("7.25"));

        assertTrue(testDao.getAllStatesTaxInfo().contains(tax));
        assertTrue(testDao.getAllStatesTaxInfo().contains(tax2));

    }

    @Test
    public void testIsValidState() throws PersistenceException, FileNotFoundException {
        Tax tax = new Tax("XX");
        tax.setStateName("Minnesota");
        tax.setTaxRate(new BigDecimal("6.875"));

        assertFalse(testDao.getAllStatesTaxInfo().contains(tax.getStateAbbreviation()));
    }

}
