/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.PersistenceException;
import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.service.InvalidDataEntryException;
import com.sg.flooringmastery.service.InvalidDateException;
import com.sg.flooringmastery.service.InvalidNameException;
import com.sg.flooringmastery.service.InvalidProductException;
import com.sg.flooringmastery.service.InvalidStateException;
import com.sg.flooringmastery.service.NoSuchOrderException;
import com.sg.flooringmastery.service.ServiceLayer;
import com.sg.flooringmastery.view.UserIO;
import com.sg.flooringmastery.view.View;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author benrickel
 */
@Component
public class Controller {

    private final View view;
    private final ServiceLayer service;
    UserIO io;

    @Autowired
    public Controller(ServiceLayer service, View view) {
        this.service = service;
        this.view = view;
    }

    public void run() throws PersistenceException {
        boolean keepGoing = true;
        int menuSelection = 0;
        while (keepGoing) {
            try {
                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        getDailyOrders();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        exportData();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            } catch (Exception e) {
                errorMessage(e.getMessage());
            }
        }
        exit();
    }

    private int getMenuSelection() throws PersistenceException, NoSuchOrderException, FileNotFoundException {
        return view.displayMenuAndGetSelection(service.getAllProducts(), service.getAllStateTaxInfo());
    }

    private void getDailyOrders() throws PersistenceException, NoSuchOrderException, InvalidDateException {
        LocalDate ld = view.getOrdersByDate();
        Collection<Order> dailyOrders = service.getOrdersByDate(ld);
        view.displayDailyOrders(dailyOrders, ld);
    }

    private void addOrder() throws PersistenceException, NoSuchOrderException, InvalidDataEntryException, InvalidDateException, InvalidNameException, InvalidProductException, InvalidStateException, IOException {

        Order order = view.getNewOrderInfo();
        service.calculateTotalCost(order);
        view.displayOrderSummary(order);
        if (view.getConfirmation() == true) {
            service.addOrder(order);
            view.displayAddOrderSuccessBanner(order);
        } else {
            throw new InvalidDataEntryException("\n=== Order Cancelled! ===");
        }
    }

    private void editOrder() throws PersistenceException, NoSuchOrderException, InvalidDataEntryException, InvalidDateException, InvalidNameException, InvalidProductException, InvalidStateException, IOException, NoSuchOrderException {

        Order order = view.getOrderDateAndNumber();
        order = service.getOrderByDateAndNumber(order);
        view.displayOrderSummary(order);
        if (view.getConfirmation() == true) {
            order = view.getEditOrderInfo(order);
            service.calculateTotalCost(order);
            view.displayOrderSummary(order);

            if (view.getConfirmation() == true) {
                service.editOrder(order);
                view.displayEditOrderSuccessBanner();
            } else {
                throw new InvalidDataEntryException("\n=== Could not Edit Order! ===");
            }

        } else {
            throw new InvalidDataEntryException("\n=== Could not Edit Order! ===");
        }
    }

    private void removeOrder() throws InvalidDateException, InvalidDataEntryException, NoSuchOrderException, InvalidProductException, InvalidNameException, InvalidStateException, PersistenceException, FileNotFoundException {
        view.displayRemoveOrderBanner();
        Order order = view.getOrderDateAndNumber();
        order = service.getOrderByDateAndNumber(order);
        view.displayOrderSummary(order);

        if (view.getConfirmation() == true) {
            service.removeOrder(order);
            view.displayRemoveOrderSuccessBanner();
        } else if (view.getConfirmation() == false) {
            throw new InvalidDataEntryException("\n=== Could not Remove Order! ===");
        }
    }

    private void exportData() throws PersistenceException, IOException, NoSuchOrderException {
        service.exportData();
        view.displayExportDataSuccessBanner();
    }

    private void exit() {
        view.displayExitMessage();
    }

    private void unknownCommand() {
        view.displayUnknownCommandMessage();
    }

    private void errorMessage(String message) {
        view.displayErrorMessage(message);
    }

}
