/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.model.Product;
import com.sg.flooringmastery.model.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author benrickel
 */
public class ServiceLayerFileImplTest {

    private ServiceLayer service;

    public ServiceLayerFileImplTest() {
        this.service = service;

    }

    @BeforeEach
    public void setUp() throws Exception {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("serviceLayer", ServiceLayer.class);
    }

    @Test
    public void testCalculateTotalCost() throws Exception {
        //matches Stub
        Order order = new Order(6);
        order.setCustomerName("Ben Rickel");
        Tax tax = new Tax("MN");
        tax.setStateName("Minnesota");
        tax.setTaxRate(new BigDecimal("6.875"));
        order.setTaxInfo(tax);
        Product product = new Product("Wood");
        order.setArea(new BigDecimal("420"));
        product.setCostPerSquareFoot(new BigDecimal("5.15"));
        product.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        order.setProductInfo(product);
        order.setOrderDate(LocalDate.parse("2021-11-23"));

        service.calculateTotalCost(order);

        assertEquals(order.getLaborCost(), new BigDecimal("1995.00"));
        assertEquals(order.getMaterialCost(), new BigDecimal("2163.00"));
        assertEquals(order.getLaborCost(), new BigDecimal("1995.00"));
        assertEquals(order.getTaxTotal(), new BigDecimal("285.86"));
        assertEquals(order.getGrandTotal(), new BigDecimal("4443.86"));

    }

    @Test
    public void testCalculateBadName() throws Exception {

        Order order = new Order(1);
        order.setOrderDate(LocalDate.parse("2021-11-23"));
        order.setCustomerName("!@#$%^&*");
        Tax tax = new Tax("MN");
        tax.setStateName("Minnesota");
        tax.setTaxRate(new BigDecimal("6.875"));
        order.setTaxInfo(tax);
        Product product = new Product("Wood");
        order.setArea(new BigDecimal("420"));
        product.setCostPerSquareFoot(new BigDecimal("5.15"));
        product.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        order.setProductInfo(product);

        try {
            service.calculateTotalCost(order);
            fail("Wrong exception thrown.");
        } catch (InvalidNameException e) {

        } catch (InvalidDataEntryException
                | InvalidDateException
                | InvalidProductException
                | InvalidStateException
                | NoSuchOrderException e) {
            fail("Wrong exception thrown!");
        }
    }

    @Test
    public void testCalculateBadDate() throws Exception {
        Order order = new Order(0);
        order.setOrderDate(LocalDate.parse("1989-11-23"));
        order.setCustomerName("Ben Rickel");
        Tax tax = new Tax("MN");
        tax.setStateName("Minnesota");
        tax.setTaxRate(new BigDecimal("6.875"));
        order.setTaxInfo(tax);
        Product product = new Product("Wood");
        order.setArea(new BigDecimal("420"));
        product.setCostPerSquareFoot(new BigDecimal("5.15"));
        product.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        order.setProductInfo(product);

        try {
            service.calculateTotalCost(order);
            fail("Wrong exception thrown.");
        } catch (InvalidDateException e) {

        } catch (InvalidDataEntryException
                | InvalidNameException
                | InvalidProductException
                | InvalidStateException
                | NoSuchOrderException e) {
            fail("Wrong exception thrown!");
        }
    }

    @Test
    public void testCalculateBadState() throws Exception {
        Order order = new Order(1);
        order.setOrderDate(LocalDate.parse("1989-11-23"));
        order.setCustomerName("Ben Rickel");
        Tax tax = new Tax("XX");
        tax.setStateName("Minnesota");
        tax.setTaxRate(new BigDecimal("6.875"));
        order.setTaxInfo(tax);
        Product product = new Product("Wood");
        order.setArea(new BigDecimal("420"));
        product.setCostPerSquareFoot(new BigDecimal("5.15"));
        product.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        order.setProductInfo(product);

        try {
            service.calculateTotalCost(order);
            fail("Wrong exception thrown.");
        } catch (InvalidStateException e) {

        } catch (InvalidDataEntryException
                | InvalidNameException
                | InvalidProductException
                | InvalidDateException
                | NoSuchOrderException e) {
            fail("Wrong exception thrown!");
        }
    }

    @Test
    public void testCalculateBadProduct() throws Exception {
        Order order = new Order(1);
        order.setOrderDate(LocalDate.parse("2021-11-23"));
        order.setCustomerName("Ben Rickel");
        Tax tax = new Tax("MN");
        tax.setStateName("Minnesota");
        tax.setTaxRate(new BigDecimal("6.875"));
        order.setTaxInfo(tax);
        Product product = new Product("Stone");
        order.setArea(new BigDecimal("420"));
        product.setCostPerSquareFoot(new BigDecimal("5.15"));
        product.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        order.setProductInfo(product);

        try {
            service.calculateTotalCost(order);
            fail("Wrong exception thrown.");
        } catch (InvalidProductException e) {

        } catch (InvalidDataEntryException
                | InvalidNameException
                | InvalidStateException
                | InvalidDateException
                | NoSuchOrderException e) {
            fail("Wrong exception thrown!");
        }
    }

    @Test
    public void testCalculateBadArea() throws Exception {
        Order order = new Order(1);
        order.setOrderDate(LocalDate.parse("2021-11-23"));
        order.setCustomerName("Ben Rickel");
        Tax tax = new Tax("MN");
        tax.setStateName("Minnesota");
        tax.setTaxRate(new BigDecimal("6.875"));
        order.setTaxInfo(tax);
        Product product = new Product("Wood");
        order.setArea(new BigDecimal("88"));
        product.setCostPerSquareFoot(new BigDecimal("5.15"));
        product.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        order.setProductInfo(product);

        try {
            service.calculateTotalCost(order);
            fail("Wrong exception thrown.");
        } catch (InvalidDataEntryException e) {

        } catch (InvalidStateException
                | InvalidNameException
                | InvalidProductException
                | InvalidDateException
                | NoSuchOrderException e) {
            fail("Wrong exception thrown!");
        }
    }

    @Test
    public void testNoSuchOrderException() throws Exception {
        Order order = new Order(99);
        order.setOrderDate(LocalDate.parse("2028-11-23"));
        order.setCustomerName("Ben");
        Tax tax = new Tax("MN");
        tax.setStateName("Minnesota");
        tax.setTaxRate(new BigDecimal("6.875"));
        order.setTaxInfo(tax);
        Product product = new Product("Wood");
        order.setArea(new BigDecimal("420"));
        product.setCostPerSquareFoot(new BigDecimal("5.15"));
        product.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        order.setProductInfo(product);

        try {
            service.getOrderByDateAndNumber(order);
            fail("Wrong exception was thrown.");
        } catch (NoSuchOrderException e) {

        } catch (InvalidDataEntryException
                | InvalidDateException e) {
            fail("Wrong exception thrown.");
        }
    }

    @Test
    public void testInvalidDataEntry() throws Exception {
        Order order = new Order(99);
        order.setCustomerName("Ben Rickel");
        Tax tax = new Tax("MN");
        tax.setStateName("Minnesota");
        tax.setTaxRate(new BigDecimal("6.875"));
        order.setTaxInfo(tax);
        Product product = new Product("Stone");
        order.setArea(new BigDecimal("450"));

        try {
            service.getOrderByDateAndNumber(order);
            fail("Did not throw exception.");
        } catch (NoSuchOrderException e) {

        }
    }

    @Test
    public void testAddOrder() throws Exception {
        //matches Stub
        Order order = new Order(1);
        order.setCustomerName("Ben Rickel");
        Tax tax = new Tax("XX");
        tax.setStateName("Minnesota");
        tax.setTaxRate(new BigDecimal("7"));
        order.setTaxInfo(tax);
        Product product = new Product("Wood");
        order.setArea(new BigDecimal("450"));
        product.setCostPerSquareFoot(new BigDecimal("2"));
        product.setLaborCostPerSquareFoot(new BigDecimal("3"));
        order.setProductInfo(product);
        order.setMaterialCost(new BigDecimal("4"));
        order.setLaborCost(new BigDecimal("6900"));
        order.setTaxTotal(new BigDecimal("42"));
        order.setGrandTotal(new BigDecimal("69000"));
        order.setOrderDate(LocalDate.parse("2021-11-23"));

        assertNotNull(order);

        Order benOrder = service.addOrder(order);

        assertNotNull(benOrder, "Adding number order 1 should not be null");
        assertEquals(order, benOrder, "Order number 1 should be for Ben.");

    }

    @Test
    public void testEditOrder() throws Exception {
        //matches Stub
        Order order = new Order(1);
        order.setCustomerName("Ben Rickel");
        Tax tax = new Tax("MN");
        tax.setStateName("Minnesota");
        tax.setTaxRate(new BigDecimal("7"));
        order.setTaxInfo(tax);
        Product product = new Product("Wood");
        order.setArea(new BigDecimal("450"));
        product.setCostPerSquareFoot(new BigDecimal("2"));
        product.setLaborCostPerSquareFoot(new BigDecimal("3"));
        order.setProductInfo(product);
        order.setMaterialCost(new BigDecimal("4"));
        order.setLaborCost(new BigDecimal("6900"));
        order.setTaxTotal(new BigDecimal("42"));
        order.setGrandTotal(new BigDecimal("69000"));
        order.setOrderDate(LocalDate.parse("2021-11-23"));

        service.editOrder(order);

        order.setOrderNumber(2);
        order.setOrderDate(LocalDate.parse("2022-04-03"));
        order.setCustomerName("Becky Lindholm");
        order.getTaxInfo().setStateAbbreviation("CA");
        order.getProductInfo().setProductType("Tile");

        assertEquals(order.getOrderNumber(), 2, "The order number should be 2.");
        assertEquals(order.getOrderDate(), LocalDate.parse("2022-04-03"));
        assertEquals(order.getCustomerName(), "Becky Lindholm");
        assertEquals(order.getTaxInfo().getStateAbbreviation(), "CA");
        assertEquals(order.getProductInfo().getProductType(), "Tile");

    }

    @Test
    public void testRemoveOrder() throws Exception {
        //matches Stub
        Order order = new Order(1);
        order.setCustomerName("Ben Rickel");
        Tax tax = new Tax("MN");
        tax.setStateName("Minnesota");
        tax.setTaxRate(new BigDecimal("7"));
        order.setTaxInfo(tax);
        Product product = new Product("Wood");
        order.setArea(new BigDecimal("450"));
        product.setCostPerSquareFoot(new BigDecimal("2"));
        product.setLaborCostPerSquareFoot(new BigDecimal("3"));
        order.setProductInfo(product);
        order.setMaterialCost(new BigDecimal("4"));
        order.setLaborCost(new BigDecimal("6900"));
        order.setTaxTotal(new BigDecimal("42"));
        order.setGrandTotal(new BigDecimal("69000"));
        order.setOrderDate(LocalDate.parse("2021-11-23"));

        Order benOrder = service.removeOrder(order);

        assertNotNull(benOrder, "Removing order 1 should not be null");
        assertEquals(order, benOrder, "Order number 1 should be for Ben.");

    }

    @Test
    public void testGetOrdersByDate() throws Exception {
        //matches Stub
        Order order = new Order(1);
        order.setCustomerName("Ben Rickel");
        Tax tax = new Tax("MN");
        tax.setStateName("Minnesota");
        tax.setTaxRate(new BigDecimal("7"));
        order.setTaxInfo(tax);
        Product product = new Product("Wood");
        order.setArea(new BigDecimal("450"));
        product.setCostPerSquareFoot(new BigDecimal("2"));
        product.setLaborCostPerSquareFoot(new BigDecimal("3"));
        order.setProductInfo(product);
        order.setMaterialCost(new BigDecimal("4"));
        order.setLaborCost(new BigDecimal("6900"));
        order.setTaxTotal(new BigDecimal("42"));
        order.setGrandTotal(new BigDecimal("69000"));
        order.setOrderDate(LocalDate.parse("2021-11-23"));

        Collection<Order> orderCollection = new ArrayList<>();
        orderCollection.add(order);

        Collection<Order> orders = service.getOrdersByDate(order.getOrderDate());

        assertNotNull(orders, "Getting orders for 11-23-2021 should not be null.");
        assertEquals(orderCollection, orders, "Order on 11-23-2021 should be for Ben");

    }

    @Test
    public void testGetOrderByDateAndNumber() throws Exception {
        //matches Stub
        Order order = new Order(1);
        order.setCustomerName("Ben Rickel");
        Tax tax = new Tax("MN");
        tax.setStateName("Minnesota");
        tax.setTaxRate(new BigDecimal("7"));
        order.setTaxInfo(tax);
        Product product = new Product("Wood");
        order.setArea(new BigDecimal("450"));
        product.setCostPerSquareFoot(new BigDecimal("2"));
        product.setLaborCostPerSquareFoot(new BigDecimal("3"));
        order.setProductInfo(product);
        order.setMaterialCost(new BigDecimal("4"));
        order.setLaborCost(new BigDecimal("6900"));
        order.setTaxTotal(new BigDecimal("42"));
        order.setGrandTotal(new BigDecimal("69000"));
        order.setOrderDate(LocalDate.parse("2021-11-23"));

        Order benOrder = service.getOrderByDateAndNumber(order);
        assertNotNull(benOrder, "Getting order 1 should not be null.");
        assertEquals(order, benOrder, "Orders should be equal");
    }

    @Test
    public void testGetAllOrders() throws Exception {
        //matches Stub
        Order order = new Order(1);
        order.setCustomerName("Ben Rickel");
        Tax tax = new Tax("MN");
        tax.setStateName("Minnesota");
        tax.setTaxRate(new BigDecimal("7"));
        order.setTaxInfo(tax);
        Product product = new Product("Wood");
        order.setArea(new BigDecimal("450"));
        product.setCostPerSquareFoot(new BigDecimal("2"));
        product.setLaborCostPerSquareFoot(new BigDecimal("3"));
        order.setProductInfo(product);
        order.setMaterialCost(new BigDecimal("4"));
        order.setLaborCost(new BigDecimal("6900"));
        order.setTaxTotal(new BigDecimal("42"));
        order.setGrandTotal(new BigDecimal("69000"));
        order.setOrderDate(LocalDate.parse("2021-11-23"));

        assertEquals(1, service.getAllOrders().size(), "Only one order.");
        assertTrue(service.getAllOrders().contains(order));
    }

    @Test
    public void testGetAllProducts() throws Exception {
        //matches Stub
        Order order = new Order(1);
        order.setCustomerName("Ben Rickel");
        Tax tax = new Tax("MN");
        tax.setStateName("Minnesota");
        tax.setTaxRate(new BigDecimal("7"));
        order.setTaxInfo(tax);
        Product product = new Product("Wood");
        order.setArea(new BigDecimal("450"));
        product.setCostPerSquareFoot(new BigDecimal("5.15"));
        product.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        order.setProductInfo(product);
        order.setMaterialCost(new BigDecimal("4"));
        order.setLaborCost(new BigDecimal("6900"));
        order.setTaxTotal(new BigDecimal("42"));
        order.setGrandTotal(new BigDecimal("69000"));
        order.setOrderDate(LocalDate.parse("2021-11-23"));

        assertEquals(1, service.getAllProducts().size(), "Product list should only have 1 product.");
        assertTrue(service.getAllProducts().contains(product));
    }

    @Test
    public void testGetAllStateTaxInfo() throws Exception {
        //matches Stub
        Order order = new Order(1);
        order.setCustomerName("Ben Rickel");
        Tax tax = new Tax("MN");
        tax.setStateName("Minnesota");
        tax.setTaxRate(new BigDecimal("6.875"));
        order.setTaxInfo(tax);
        Product product = new Product("Wood");
        order.setArea(new BigDecimal("450"));
        product.setCostPerSquareFoot(new BigDecimal("5.15"));
        product.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        order.setProductInfo(product);
        order.setMaterialCost(new BigDecimal("4"));
        order.setLaborCost(new BigDecimal("6900"));
        order.setTaxTotal(new BigDecimal("42"));
        order.setGrandTotal(new BigDecimal("69000"));
        order.setOrderDate(LocalDate.parse("2021-11-23"));

        assertEquals(1, service.getAllStateTaxInfo().size(), "Product list should only have 1 product.");
        assertTrue(service.getAllStateTaxInfo().contains(tax));
    }

    @Test
    public void testExportData() throws Exception {
        Order order = new Order(1);
        order.setCustomerName("Ben Rickel");
        Tax tax = new Tax("MN");
        tax.setTaxRate(new BigDecimal("6.875"));
        order.setTaxInfo(tax);
        Product product = new Product("Tile");
        order.setArea(new BigDecimal("420.00"));
        product.setCostPerSquareFoot(new BigDecimal("8.00"));
        product.setLaborCostPerSquareFoot(new BigDecimal("15.00"));
        order.setProductInfo(product);
        order.setMaterialCost(new BigDecimal("5.00"));
        order.setLaborCost(new BigDecimal("5000.00"));
        order.setTaxTotal(new BigDecimal("750.00"));
        order.setGrandTotal(new BigDecimal("10000.00"));
        order.setOrderDate(LocalDate.parse("2019-11-23"));

        List<Order> orderList = new ArrayList();

        orderList.add(order);

        assertTrue(orderList.contains(order));

        service.exportData();

    }
}
