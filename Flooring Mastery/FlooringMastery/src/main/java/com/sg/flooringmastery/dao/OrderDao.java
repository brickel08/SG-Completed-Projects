/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Order;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author benrickel
 */
public interface OrderDao {

    public Order addOrder(int orderNumber, Order order) throws PersistenceException, IOException;

    public Collection<Order> getOrdersByDate(LocalDate orderDate) throws PersistenceException;

    public Order getOrderByDateAndNumber(Order order) throws PersistenceException;

    public List<Order> getAllOrders() throws PersistenceException;

    public Order editOrder(Order order) throws PersistenceException, IOException;

    public Order removeOrder(Order order) throws PersistenceException, IOException;

    public boolean isValidNumber(Order order) throws PersistenceException;

    public boolean isValidDate(Order order) throws PersistenceException;

}
