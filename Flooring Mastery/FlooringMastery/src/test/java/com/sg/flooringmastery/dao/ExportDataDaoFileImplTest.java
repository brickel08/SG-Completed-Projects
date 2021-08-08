/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.model.Product;
import com.sg.flooringmastery.model.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author benrickel
 */
public class ExportDataDaoFileImplTest {

    ExportDataDaoFileImpl testDao;

    public ExportDataDaoFileImplTest() {
    }

    @BeforeEach
    public void setUp() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testDao = ctx.getBean("exportDataDao", ExportDataDaoFileImpl.class);
    }

    @Test
    public void writeData() throws Exception {
        Order order = new Order(1);
        order.setCustomerName("Ben Rickel");
        Tax tax = new Tax("MN");
        tax.setTaxRate(new BigDecimal("6.785"));
        order.setTaxInfo(tax);
        Product product = new Product("Wood");
        product.setCostPerSquareFoot(new BigDecimal("8.00"));
        product.setLaborCostPerSquareFoot(new BigDecimal("15.00"));
        order.setProductInfo(product);
        order.setArea(new BigDecimal("420.00"));
        order.setMaterialCost(new BigDecimal("5.00"));
        order.setLaborCost(new BigDecimal("5000.00"));
        order.setTaxTotal(new BigDecimal("750.00"));
        order.setGrandTotal(new BigDecimal("10000.00"));
        order.setOrderDate(LocalDate.parse("2021-11-23"));

        Order order2 = new Order(2);
        order2.setCustomerName("Becky Lindholm");
        Tax tax2 = new Tax("CA");
        tax2.setTaxRate(new BigDecimal("6.00"));
        order2.setTaxInfo(tax);
        Product product2 = new Product("Tile");
        product2.setCostPerSquareFoot(new BigDecimal("8.00"));
        product2.setLaborCostPerSquareFoot(new BigDecimal("15.00"));
        order2.setProductInfo(product);
        order2.setArea(new BigDecimal("420.00"));
        order2.setMaterialCost(new BigDecimal("5.00"));
        order2.setLaborCost(new BigDecimal("5000.00"));
        order2.setTaxTotal(new BigDecimal("750.00"));
        order2.setGrandTotal(new BigDecimal("10000.00"));
        order2.setOrderDate(LocalDate.parse("2021-11-23"));

        List<Order> orderList = new ArrayList();
        orderList.add(order);
        orderList.add(order2);

        testDao.writeData(orderList);

        assertNotNull(orderList, "The list of Orders should not be null.");
        assertEquals(2, orderList.size(), "The size of the Order list should be 2.");
    }
}
