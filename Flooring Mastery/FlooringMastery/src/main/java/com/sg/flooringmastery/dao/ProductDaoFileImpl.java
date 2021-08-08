/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Product;
import com.sg.flooringmastery.service.NoSuchOrderException;
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
public class ProductDaoFileImpl implements ProductDao {

    public final String PRODUCTS_FOLDER;
    public static final String DELIMITER = ",";

    public ProductDaoFileImpl() {
        PRODUCTS_FOLDER = "/Users/benrickel/Downloads/online-java-2019-brickel08-master 3/Summatives/m5 summative/Flooring Mastery/FlooringMastery/Data";
    }

    @Autowired
    public ProductDaoFileImpl(String productsTextFile) {
        this.PRODUCTS_FOLDER = productsTextFile;
    }

    public Map<String, Product> productsMap = new HashMap<>();

    @Override
    public Product getProductInfo(String productType) throws PersistenceException, NoSuchOrderException {
        try {
            loadProductInfo();
            return productsMap.get(productType);
        } catch (NoSuchOrderException e) {
            throw new NoSuchOrderException("\n=== Could Not Persist Data! ===", e);
        }
    }

    @Override
    public List<Product> getAllProducts() throws PersistenceException, NoSuchOrderException {
        try {
            loadProductInfo();
            return new ArrayList<Product>(productsMap.values());
        } catch (PersistenceException e) {
            throw new PersistenceException("\n=== Could Not Persist Data! ===", e);
        }
    }

    @Override
    public boolean isValidProduct(String productType) throws PersistenceException, NoSuchOrderException {
        try {
            loadProductInfo();
            return productsMap.containsKey(productType);
        } catch (PersistenceException | NoSuchOrderException e) {
            throw new PersistenceException("\n=== Could not persist data! ===", e);
        }
    }

    private void loadProductInfo() throws PersistenceException, NoSuchOrderException {
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(PRODUCTS_FOLDER)));
        } catch (FileNotFoundException e) {
            throw new NoSuchOrderException("\n=== Could not load Product data! ===", e);
        }

        String currentLine;
        String[] currentTokens;

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);

            Product product = new Product(currentTokens[0]);
            product.setCostPerSquareFoot(new BigDecimal(currentTokens[1]));
            product.setLaborCostPerSquareFoot(new BigDecimal(currentTokens[2]));

            productsMap.put(product.getProductType(), product);
        }
        scanner.close();
    }
}
