/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Tax;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author benrickel
 */
@Component
public class TaxDaoFileImpl implements TaxDao {

    @Autowired
    private final String TAXES_FOLDER;
    public static final String DELIMITER = ",";

    public TaxDaoFileImpl() {
        TAXES_FOLDER = "/Users/benrickel/Downloads/online-java-2019-brickel08-master 3/Summatives/m5 summative/Flooring Mastery/FlooringMastery/Data";
    }

    public TaxDaoFileImpl(String taxesTextFile) {
        TAXES_FOLDER = taxesTextFile;
    }

    private Map<String, Tax> taxesMap = new HashMap<>();

    @Override
    public Tax getStateTaxInfo(String stateAbbreviation) throws PersistenceException {
        try {
            loadTaxInfo();
            return taxesMap.get(stateAbbreviation);
        } catch (PersistenceException e) {
            throw new PersistenceException("\n=== Could not persist data! ===", e);
        }
    }

    @Override
    public List<Tax> getAllStatesTaxInfo() throws PersistenceException {
        try {
            loadTaxInfo();
            return new ArrayList<Tax>(taxesMap.values());
        } catch (PersistenceException e) {
            throw new PersistenceException("\n=== Could not persist data! ===", e);
        }
    }

    @Override
    public boolean isValidState(String stateAbbreviation) throws PersistenceException {
        try {
            loadTaxInfo();
            return taxesMap.containsKey(stateAbbreviation);
        } catch (PersistenceException e) {
            throw new PersistenceException("\n=== Could not persist data! ===", e);
        }
    }

    private void loadTaxInfo() throws PersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(TAXES_FOLDER)));
        } catch (FileNotFoundException e) {
            throw new PersistenceException("\n=== Could not load Tax data! ===", e);
        }

        String currentLine;
        String[] currentTokens;

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);

            Tax tax = new Tax(currentTokens[0]);
            tax.setStateName(currentTokens[1]);
            tax.setTaxRate(new BigDecimal(currentTokens[2]));

            taxesMap.put(tax.getStateAbbreviation(), tax);
        }
        scanner.close();
    }
}
