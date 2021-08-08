/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Product;
import com.sg.flooringmastery.service.NoSuchOrderException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author benrickel
 */
public class ProductDaoStubImpl implements ProductDao {

    public Product testProduct;

    public ProductDaoStubImpl() {
        testProduct = new Product("Wood");
        testProduct.setCostPerSquareFoot(new BigDecimal("5.15"));
        testProduct.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
    }

    public ProductDaoStubImpl(Product testProduct) {
        this.testProduct = testProduct;
    }

    @Override
    public Product getProductInfo(String productType) throws PersistenceException, NoSuchOrderException {
        if (productType.equals(testProduct.getProductType())) {
            return testProduct;
        } else {
            return null;
        }
    }

    @Override
    public List<Product> getAllProducts() throws PersistenceException, NoSuchOrderException {
        List<Product> productList = new ArrayList<>();
        productList.add(testProduct);
        return productList;
    }

    @Override
    public boolean isValidProduct(String productType) throws PersistenceException, NoSuchOrderException {
        if (productType.contains(testProduct.getProductType())) {
            return true;
        } else {
            return false;
        }
    }
}
