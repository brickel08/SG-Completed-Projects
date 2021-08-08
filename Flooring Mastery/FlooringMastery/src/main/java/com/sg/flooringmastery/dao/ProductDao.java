/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Product;
import com.sg.flooringmastery.service.NoSuchOrderException;
import java.util.List;

/**
 *
 * @author benrickel
 */
public interface ProductDao {

    Product getProductInfo(String productType) throws PersistenceException, NoSuchOrderException;

    List<Product> getAllProducts() throws PersistenceException, NoSuchOrderException;

    boolean isValidProduct(String productType) throws PersistenceException, NoSuchOrderException;

}
