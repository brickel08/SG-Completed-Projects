/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Product;
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
public class ProductDaoFileImplTest {

    ProductDaoFileImpl testDao;

    public ProductDaoFileImplTest() {
    }

    @BeforeEach
    public void setUp() throws Exception {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testDao = ctx.getBean("productDao", ProductDaoFileImpl.class);
    }

    @Test
    public void testGetProductInfo() throws Exception {

        Product product = new Product("Carpet");
        product.setCostPerSquareFoot(new BigDecimal("2.25"));
        product.setLaborCostPerSquareFoot(new BigDecimal("2.10"));

        Product retrievedProduct = testDao.getProductInfo("Carpet");

        assertEquals(product.getProductType(), retrievedProduct.getProductType(), "Checking product type.");
        assertEquals(product.getCostPerSquareFoot(), retrievedProduct.getCostPerSquareFoot(), "Checking cost per square foot.");
        assertEquals(product.getLaborCostPerSquareFoot(), retrievedProduct.getLaborCostPerSquareFoot(), "Checking labor cost per square foot.");
    }

    @Test
    public void testGetAllProducts() throws Exception {
        Product product = new Product("Carpet");
        product.setCostPerSquareFoot(new BigDecimal("2.25"));
        product.setLaborCostPerSquareFoot(new BigDecimal("2.10"));

        Product product2 = new Product("Tile");
        product2.setCostPerSquareFoot(new BigDecimal("2.25"));
        product2.setLaborCostPerSquareFoot(new BigDecimal("2.10"));

        assertTrue(testDao.getAllProducts().contains(product), "The list of Products should include Carpet.");
        assertTrue(testDao.getAllProducts().contains(product2), "The list of Products should include Tile.");
    }

    @Test
    public void testIsValidProduct() throws Exception {
        Product product = new Product("Garbage");
        product.setCostPerSquareFoot(new BigDecimal("2.25"));
        product.setLaborCostPerSquareFoot(new BigDecimal("2.10"));

        assertFalse(testDao.getAllProducts().contains(product.getProductType()));
    }
}
