/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.OrderDao;
import com.sg.flooringmastery.dao.PersistenceException;
import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.model.Product;
import com.sg.flooringmastery.model.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author benrickel
 */
public class OrderDaoStubImpl implements OrderDao {

    public Order testOrder;
    public Tax testTax;
    public Product testProduct;

    public OrderDaoStubImpl() {
        testOrder = new Order(1);
        testOrder.setCustomerName("Ben Rickel");
        testTax = new Tax("MN");
        testTax.setStateName("Minnesota");
        testTax.setTaxRate(new BigDecimal("7"));
        testOrder.setTaxInfo(testTax);
        testProduct = new Product("Wood");
        testOrder.setArea(new BigDecimal("450"));
        testProduct.setCostPerSquareFoot(new BigDecimal("2"));
        testProduct.setLaborCostPerSquareFoot(new BigDecimal("3"));
        testOrder.setProductInfo(testProduct);
        testOrder.setMaterialCost(new BigDecimal("4"));
        testOrder.setLaborCost(new BigDecimal("6900"));
        testOrder.setTaxTotal(new BigDecimal("42"));
        testOrder.setGrandTotal(new BigDecimal("69000"));
        testOrder.setOrderDate(LocalDate.parse("2021-11-23"));
    }

    public OrderDaoStubImpl(Order testOrder) {
        this.testOrder = testOrder;
    }

    @Override
    public Order addOrder(int orderNumber, Order order) throws PersistenceException {
        if (testOrder.getOrderNumber() == orderNumber) {
            return testOrder;
        } else {
            return null;
        }
    }

    @Override
    public Collection<Order> getOrdersByDate(LocalDate orderDate) throws PersistenceException {
        if (orderDate.equals(testOrder.getOrderDate())) {
            Collection<Order> orderCollection = new ArrayList<>();
            orderCollection.add(testOrder);
            return orderCollection;
        } else {
            return null;
        }
    }

    @Override
    public Order getOrderByDateAndNumber(Order order) throws PersistenceException {
        if (testOrder.getOrderDate().equals(order.getOrderDate()) && testOrder.getOrderNumber().equals(order.getOrderNumber())) {
            return testOrder;
        } else {
            return null;
        }
    }

    @Override
    public Order editOrder(Order order) throws PersistenceException {
        if (testOrder.getOrderDate().equals(order.getOrderDate()) && testOrder.getOrderNumber().equals(order.getOrderNumber())) {
            return testOrder;
        } else {
            return null;
        }
    }

    @Override
    public Order removeOrder(Order order) throws PersistenceException {
        if (testOrder.getOrderDate().equals(order.getOrderDate()) && testOrder.getOrderNumber().equals(order.getOrderNumber())) {
            return testOrder;
        } else {
            return null;
        }
    }

    @Override
    public List<Order> getAllOrders() throws PersistenceException {
        List<Order> orders = new ArrayList<>();
        orders.add(testOrder);
        return orders;
    }

    @Override
    public boolean isValidDate(Order order) throws PersistenceException {
        if (order.getOrderDate().isEqual(testOrder.getOrderDate())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isValidNumber(Order order) throws PersistenceException {
        if (order.getOrderNumber() == testOrder.getOrderNumber()) {
            return true;
        } else {
            return false;
        }
    }
}
