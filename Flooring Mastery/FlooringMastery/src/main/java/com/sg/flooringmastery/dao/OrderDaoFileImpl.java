/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.model.Product;
import com.sg.flooringmastery.model.Tax;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author benrickel
 */
@Component
public class OrderDaoFileImpl implements OrderDao {

    private Map<LocalDate, Map<Integer, Order>> ordersMap = new HashMap<>();

    public String ORDERS_FOLDER;
    public static final String DELIMITER = ",";

    public OrderDaoFileImpl() {
        ORDERS_FOLDER = "/Users/benrickel/Downloads/online-java-2019-brickel08-master 3/Summatives/m5 summative/Flooring Mastery/FlooringMastery";

    }

    @Autowired
    public OrderDaoFileImpl(String ORDERS_FOLDER) {
        this.ORDERS_FOLDER = ORDERS_FOLDER;
    }

    @Override
    public Order addOrder(int orderNumber, Order order) throws PersistenceException {
        loadOrders();
        Map<Integer, Order> orders = ordersMap.get(order.getOrderDate());
        if (orders == null) {
            Map<Integer, Order> newOrder = new HashMap<>();
            newOrder.put(order.getOrderNumber(), order);
            ordersMap.put(order.getOrderDate(), newOrder);
            writeOrders();
            return order;
        } else {
            orders.put(order.getOrderNumber(), order);
            writeOrders();
            return order;
        }
    }

    @Override
    public Collection<Order> getOrdersByDate(LocalDate orderDate) throws PersistenceException {

        try {
            loadOrders();
            if (ordersMap.containsKey(orderDate) == false) {
                throw new PersistenceException("\n=== Could not persist data! ===");
            }
            return ordersMap.get(orderDate).values();
        } catch (PersistenceException e) {
            throw new PersistenceException("\n=== No Orders on " + orderDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) + "! ===", e);
        }
    }

    @Override
    public Order getOrderByDateAndNumber(Order order) throws PersistenceException {
        loadOrders();
        Map<Integer, Order> orders = ordersMap.get(order.getOrderDate());
        if (orders == null) {
            throw new PersistenceException("\n=== Could not persist data! ===");
        }
        order = orders.get(order.getOrderNumber());
        return order;
    }

    @Override
    public boolean isValidDate(Order order) throws PersistenceException {
        loadOrders();
        return ordersMap.containsKey(order.getOrderDate());
    }

    @Override
    public boolean isValidNumber(Order order) throws PersistenceException {
        loadOrders();
        Map<Integer, Order> orders = ordersMap.get(order.getOrderDate());
        return orders.containsKey(order.getOrderNumber());
    }

    @Override
    public List<Order> getAllOrders() throws PersistenceException {
        try {
            loadOrders();
            Set<LocalDate> orderDates = ordersMap.keySet();
            List<Order> orders = new ArrayList();
            for (LocalDate day : orderDates) {
                orders.addAll(ordersMap.get(day).values());
            }
            return orders;
        } catch (PersistenceException e) {
            throw new PersistenceException("\n=== Could not persist data! ===", e);
        }
    }

    @Override
    public Order editOrder(Order order) throws PersistenceException {
        try {
            loadOrders();
            Order replacedOrder = getOrderByDateAndNumber(order);
            Map<Integer, Order> orders = new HashMap<>();
            orders.put(order.getOrderNumber(), order);
            replacedOrder = ordersMap.get(order.getOrderDate()).replace(order.getOrderNumber(), order);
            writeOrders();
            return replacedOrder;
        } catch (PersistenceException e) {
            throw new PersistenceException("Could not persist data.", e);
        }
    }

    @Override
    public Order removeOrder(Order order) throws PersistenceException {
        try {
            loadOrders();
            Order removedOrder = ordersMap.get(order.getOrderDate()).remove(order.getOrderNumber());
            writeOrders();
            return removedOrder;
        } catch (PersistenceException e) {
            throw new PersistenceException("\n=== Could not persist data! ===", e);
        }
    }

    private Order unmarshallData(String currentLine) {

        String[] currentTokens;

        currentTokens = currentLine.split(DELIMITER);
        Order currentOrderInfo = new Order(Integer.parseInt(currentTokens[0]));
        currentOrderInfo.setCustomerName(currentTokens[1]);

        Tax currentTaxInfo = new Tax((currentTokens[2]));
        currentTaxInfo.setTaxRate(new BigDecimal(currentTokens[3]));
        currentOrderInfo.setTaxInfo(currentTaxInfo);

        Product currentProductInfo = new Product(currentTokens[4]);
        currentOrderInfo.setArea(new BigDecimal(currentTokens[5]));
        currentProductInfo.setCostPerSquareFoot(new BigDecimal(currentTokens[6]));
        currentProductInfo.setLaborCostPerSquareFoot(new BigDecimal(currentTokens[7]));
        currentOrderInfo.setProductInfo(currentProductInfo);

        currentOrderInfo.setMaterialCost(new BigDecimal(currentTokens[8]));
        currentOrderInfo.setLaborCost(new BigDecimal(currentTokens[9]));
        currentOrderInfo.setTaxTotal(new BigDecimal(currentTokens[10]));
        currentOrderInfo.setGrandTotal(new BigDecimal(currentTokens[11]));

        return currentOrderInfo;
    }

    private String marshallData(Order currentOrder) throws PersistenceException {

        String currentLine = (currentOrder.getOrderNumber() + DELIMITER
                + currentOrder.getCustomerName() + DELIMITER
                + currentOrder.getTaxInfo().getStateAbbreviation() + DELIMITER
                + currentOrder.getTaxInfo().getTaxRate() + DELIMITER
                + currentOrder.getProductInfo().getProductType() + DELIMITER
                + currentOrder.getArea() + DELIMITER
                + currentOrder.getProductInfo().getCostPerSquareFoot() + DELIMITER
                + currentOrder.getProductInfo().getLaborCostPerSquareFoot() + DELIMITER
                + currentOrder.getMaterialCost() + DELIMITER
                + currentOrder.getLaborCost() + DELIMITER
                + currentOrder.getTaxTotal() + DELIMITER
                + currentOrder.getGrandTotal());
        return currentLine;
    }

    private void loadOrders() throws PersistenceException {

        File directory = new File(ORDERS_FOLDER);
        String[] orderFiles = directory.list();

        for (String fileName : orderFiles) {
            try {
                String dateString = fileName.split("_")[1].split("\\.")[0];
                LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("MMddyyyy"));

                Scanner scanner = new Scanner(new BufferedReader(new FileReader(new File(ORDERS_FOLDER + fileName))));

                Map<Integer, Order> mapValues = new HashMap<>();

                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }

                while (scanner.hasNextLine()) {
                    String orderString = scanner.nextLine();
                    Order order = unmarshallData(orderString);
                    order.setOrderDate(date);
                    mapValues.put(order.getOrderNumber(), order);
                }
                ordersMap.put(date, mapValues);

                scanner.close();

            } catch (FileNotFoundException e) {
                throw new PersistenceException("\n=== Could not Load Orders! ===", e);
            }
        }
    }

    private void writeOrders() throws PersistenceException {

        try {
            Set<LocalDate> orderDates = ordersMap.keySet();

            for (LocalDate day : orderDates) {
                String ld = day.format(DateTimeFormatter.ofPattern("MMddyyyy"));
                String fileName = "Orders_" + ld + ".txt";
                PrintWriter out = new PrintWriter(new FileWriter(new File(ORDERS_FOLDER + fileName)));

                Collection<Order> orderValues = ordersMap.get(day).values();

                String header = "OrderNumber,CustomerName,State,TaxRate,"
                        + "ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,"
                        + "MaterialCost,LaborCost,Tax,Total";
                out.println(header);

                for (Order orders : orderValues) {
                    String orderString = marshallData(orders);
                    out.println(orderString);
                    out.flush();
                }
                out.close();
            }

        } catch (IOException e) {
            throw new PersistenceException("\n=== Could not persist data! ===", e);
        }
    }
}
