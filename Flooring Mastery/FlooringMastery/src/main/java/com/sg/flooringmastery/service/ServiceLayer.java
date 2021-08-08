/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.PersistenceException;
import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.model.Product;
import com.sg.flooringmastery.model.Tax;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author benrickel
 */
public interface ServiceLayer {

    public Order calculateTotalCost(Order order) throws PersistenceException,
            InvalidDataEntryException, InvalidDateException, InvalidNameException,
            InvalidProductException, InvalidStateException, IOException, NoSuchOrderException;

    public Order addOrder(Order order) throws PersistenceException;

    public Order editOrder(Order order) throws PersistenceException, NoSuchOrderException;

    public Order removeOrder(Order order) throws InvalidDateException, InvalidDataEntryException, PersistenceException, NoSuchOrderException;

    public Collection<Order> getOrdersByDate(LocalDate orderDate) throws PersistenceException, NoSuchOrderException;

    public Order getOrderByDateAndNumber(Order order) throws PersistenceException, NoSuchOrderException, InvalidDateException, InvalidDataEntryException;

    public void exportData() throws PersistenceException, NoSuchOrderException, IOException;

    public List<Product> getAllProducts() throws PersistenceException;

    public List<Order> getAllOrders() throws PersistenceException;

    public List<Tax> getAllStateTaxInfo() throws PersistenceException;

}
