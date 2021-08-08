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
import java.util.Collection;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author benrickel
 */
public class OrderDaoFileImplTest {

    OrderDao testDao;

    public OrderDaoFileImplTest() {
    }

    @BeforeEach
    public void setUp() throws Exception {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testDao = ctx.getBean("orderDao", OrderDaoFileImpl.class);

    }

    @Test
    public void testAddGetOrdersByDate() throws Exception {

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

        testDao.addOrder(1, order);
        testDao.addOrder(2, order2);

        Collection<Order> retrievedOrders = testDao.getOrdersByDate(LocalDate.parse("2021-11-23"));

        assertNotNull(retrievedOrders, "The list of Orders should not be null.");
        assertEquals(2, retrievedOrders.size(), "The list of Orders should have 2 Orders.");

        assertTrue(testDao.getAllOrders().contains(order), "The list of Orders should include Ben Rickel's Order.");
        assertTrue(testDao.getAllOrders().contains(order2), "The list of Orders should include Becky Lindholm's Order.");

    }

    @Test
    public void getOrderByDateAndNumber() throws Exception {
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

        testDao.addOrder(1, order);

        Order retrievedOrder = testDao.getOrderByDateAndNumber(order);

        assertEquals(order.getOrderNumber(), retrievedOrder.getOrderNumber(), "Checking Order number.");
        assertEquals(order.getCustomerName(), retrievedOrder.getCustomerName(), "Checking Customer name.");
        assertEquals(order.getTaxInfo().getStateAbbreviation(), retrievedOrder.getTaxInfo().getStateAbbreviation(), "Checking State abbreviation.");
        assertEquals(order.getTaxInfo().getTaxRate(), retrievedOrder.getTaxInfo().getTaxRate(), "Checking tax rate.");
        assertEquals(order.getProductInfo().getProductType(), retrievedOrder.getProductInfo().getProductType(), "Checking product type.");
        assertEquals(order.getArea(), retrievedOrder.getArea(), "Checking area.");
        assertEquals(order.getProductInfo().getCostPerSquareFoot(), retrievedOrder.getProductInfo().getCostPerSquareFoot(), "Checking cost per square foot.");
        assertEquals(order.getMaterialCost(), retrievedOrder.getMaterialCost(), "Checking material cost.");
        assertEquals(order.getLaborCost(), retrievedOrder.getLaborCost(), "Checking labor cost.");
        assertEquals(order.getTaxTotal(), retrievedOrder.getTaxTotal(), "Checking the total tax.");
        assertEquals(order.getGrandTotal(), retrievedOrder.getGrandTotal(), "Checking the grand total.");
        assertEquals(order.getOrderDate(), retrievedOrder.getOrderDate(), "Checking Order date.");

    }

    @Test
    public void testAddGetAllOrders() throws Exception {
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

        testDao.addOrder(1, order);
        testDao.addOrder(2, order2);

        List<Order> allOrders = testDao.getAllOrders();

        assertNotNull(allOrders, "The list of Orders should not be null.");
        assertEquals(2, allOrders.size(), "The list of Orders should have 2 entries.");

        assertTrue(testDao.getAllOrders().contains(order), "The list of Orders should include Order number 1.");
        assertTrue(testDao.getAllOrders().contains(order2), "The list of Orders should include Order number 2.");
    }

    @Test
    public void testAddEditOrder() throws Exception {
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

        testDao.addOrder(1, order);

        assertEquals(order.getOrderNumber(), 1, "The order number should be 1.");
        assertEquals(order.getOrderDate(), LocalDate.parse("2021-11-23"));
        assertEquals(order.getCustomerName(), "Ben Rickel");
        assertEquals(order.getTaxInfo().getStateAbbreviation(), "MN");
        assertEquals(order.getProductInfo().getProductType(), "Wood");

        testDao.editOrder(order);

        order.getOrderNumber();
        order.getOrderDate();
        order.setCustomerName("Becky Lindholm");
        order.getTaxInfo().setStateAbbreviation("CA");
        order.getProductInfo().setProductType("Tile");

        assertEquals(order.getOrderNumber(), 1, "The order number should be 2.");
        assertEquals(order.getOrderDate(), LocalDate.parse("2021-11-23"));
        assertEquals(order.getCustomerName(), "Becky Lindholm");
        assertEquals(order.getTaxInfo().getStateAbbreviation(), "CA");
        assertEquals(order.getProductInfo().getProductType(), "Tile");

        List<Order> orders = testDao.getAllOrders();

        assertFalse(orders.contains(order));

    }

    @Test
    public void testAddRemoveOrder() throws Exception {
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

        testDao.addOrder(1, order);
        testDao.addOrder(2, order2);

        Order removedOrder = testDao.removeOrder(order);

        assertEquals(removedOrder, order, "The removed Order was for Ben Rickel.");

        Collection<Order> allOrders = testDao.getAllOrders();

        assertNotNull(allOrders, "All Orders list should not be null");
        assertEquals(1, allOrders.size(), "All Orders should only have 1 entry.");

        assertFalse(allOrders.contains(order), "All Orders should not include Ben Rickel.");
        assertTrue(allOrders.contains(order2), "All order should still include Becky Lindholm.");

        Order secondRemoved = testDao.removeOrder(order2);
        assertEquals(secondRemoved, order2, "The removed Order should be for Becky Lindholm.");

        allOrders = testDao.getAllOrders();

        assertTrue(allOrders.isEmpty(), "The list of Orders should be empty.");

        Order retrievedOrder1 = testDao.getOrderByDateAndNumber(order);
        assertNull(retrievedOrder1, "Ben Rickel's Order was removed. Should be null.");

        Order retrievedOrder2 = testDao.getOrderByDateAndNumber(order2);
        assertNull(retrievedOrder2, "Becky Lindholm's Order was removed. Should be null.");
    }

    @Test
    public void testIsValidDate() throws Exception {
        Order order = new Order(1);
        order.setOrderDate(LocalDate.parse("2099-11-23"));

        assertFalse(testDao.getAllOrders().contains(order.getOrderDate()));
        assertFalse(testDao.getAllOrders().contains(LocalDate.parse("1989-11-23")));
    }

    @Test
    public void testIsValidNumber() throws Exception {
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

        Order order2 = new Order(10);
        order2.setCustomerName("Ben Rickel");
        Tax tax2 = new Tax("MN");
        tax2.setTaxRate(new BigDecimal("6.785"));
        order2.setTaxInfo(tax);
        Product product2 = new Product("Wood");
        product2.setCostPerSquareFoot(new BigDecimal("8.00"));
        product2.setLaborCostPerSquareFoot(new BigDecimal("15.00"));
        order2.setProductInfo(product);
        order2.setArea(new BigDecimal("420.00"));
        order2.setMaterialCost(new BigDecimal("5.00"));
        order2.setLaborCost(new BigDecimal("5000.00"));
        order2.setTaxTotal(new BigDecimal("750.00"));
        order2.setGrandTotal(new BigDecimal("10000.00"));
        order2.setOrderDate(LocalDate.parse("2021-11-23"));

        assertTrue(testDao.getAllOrders().contains(order));
        assertFalse(testDao.getAllOrders().contains(order2));

    }
}
