/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.view;

import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.model.Product;
import com.sg.flooringmastery.model.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author benrickel
 */
@Component
public class View {

    private UserIO io;

    @Autowired
    public View(UserIO io) {
        this.io = io;
    }

    public int displayMenuAndGetSelection(List<Product> productList, List<Tax> taxInfo) {

        io.print("\n======[ FLOORING MASTERY ]======");
        io.print("\n--------------------------------");
        io.print("-----< Available Products >-----");

        for (Product product : productList) {
            io.print("--------------------------------");
            io.print("Product Type: " + product.getProductType());
            io.print("Cost Per Square Foot: $" + product.getCostPerSquareFoot());
            io.print("Labor Cost Per Square Foot: $" + product.getLaborCostPerSquareFoot());
        }
        io.print("--------------------------------");

        io.print("-------< States We Serve >------");

        for (Tax tax : taxInfo) {
            io.print("--------------------------------");
            io.print(tax.getStateName() + " - " + tax.getStateAbbreviation() + " - Tax Rate: " + tax.getTaxRate() + "%");
        }
        io.print("--------------------------------");
        io.print("\n==========[ Main Menu ]=========");
        io.print("\n1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export All Data");
        return io.readInt("6. Exit", 1, 6);

    }

    public Order getOrderDateAndNumber() {
        LocalDate ld = io.readLocalDate("\nPlease enter Date of Installation. (MM-dd-yyyy)");
        int orderNumber = io.readInt("\nPlease enter the Order Number.");

        Order order = new Order(orderNumber);
        order.setOrderNumber(orderNumber);
        order.setOrderDate(ld);
        return order;
    }

    public Integer getOrderNumber() {
        Integer number = io.readInt("\nPlease enter the Order Number:");
        return number;
    }

    public LocalDate getOrdersByDate() {
        LocalDate ld = io.readLocalDate("\nPlease enter Date of Installation. (MM-dd-yyyy)");
        return ld;
    }

    public void displayOrderSummary(Order order) {

        io.print("\n=== Order Summary ===");
        io.print("Order Date: " + order.getOrderDate().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
        io.print("Customer Name: " + order.getCustomerName());
        io.print("Customer State: " + order.getTaxInfo().getStateAbbreviation());
        io.print("Product Type: " + order.getProductInfo().getProductType());
        io.print("Area: " + order.getArea() + " sq ft");
        io.print("Material Cost: $" + order.getMaterialCost());
        io.print("Labor Cost: $" + order.getLaborCost());
        io.print(("Tax Total: $" + order.getTaxTotal()));
        io.print("\nGrand Total: $" + order.getGrandTotal());

    }

    public void displayDailyOrders(Collection<Order> orderList, LocalDate date) {
        io.print("\n=== All Orders on " + date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) + " ===");

        for (Order currentOrder : orderList) {
            io.print("\nOrder Number: " + currentOrder.getOrderNumber());
            io.print("Customer Name: " + currentOrder.getCustomerName());
            io.print("Home State: " + currentOrder.getTaxInfo().getStateAbbreviation());
            io.print("Product Type: " + currentOrder.getProductInfo().getProductType());
            io.print("Area: " + currentOrder.getArea() + " sq ft");
            io.print("Material Cost: $" + currentOrder.getMaterialCost());
            io.print("Labor Cost: $" + currentOrder.getLaborCost());
            io.print(("Tax Total: $" + currentOrder.getTaxTotal()));
            io.print("Grand Total: $" + currentOrder.getGrandTotal());
        }

        io.readString("\n=== Hit ENTER to Return to Main Menu ===");

    }

    public Order getNewOrderInfo() {
        io.print("\n=== Add Order ===");
        String customerName = io.readString("\nPlease enter First and Last Name:");
        LocalDate date = io.readLocalDate("Please enter Desired Date of Installation (MM-dd-yyyy):");
        String abbreviation = io.readString("Please enter State Abbreviation:").toUpperCase();
        String productType = io.readString("Please enter Product Type:");
        BigDecimal area = io.readBigDecimal("Please enter the Area:");

        Order order = new Order(0);
        Tax tax = new Tax(abbreviation);
        Product product = new Product(productType);
        order.setCustomerName(customerName);
        order.setOrderDate(date);
        order.setTaxInfo(tax);
        order.setProductInfo(product);
        order.setArea(area);

        return order;
    }

    public Order getEditOrderInfo(Order order) {
        io.print("\n=== Edit Order ===");
        String customerName = io.readString("\nPlease enter customer First and Last name (" + order.getCustomerName() + "): ");
        String abbreviation = io.readString("Please enter the State Abbreviation (" + order.getTaxInfo().getStateAbbreviation() + "): ");
        String productType = io.readString("Please enter the Product Type (" + order.getProductInfo().getProductType() + "): ");
        BigDecimal area = io.readBigDecimal("Please enter the Area (" + order.getArea() + "): ");

        order.setCustomerName(customerName);
        order.getTaxInfo().setStateAbbreviation(abbreviation);
        order.getProductInfo().setProductType(productType);
        order.setArea(area);

        return order;
    }

    public boolean getConfirmation() {
        String confirm = io.readString("\n=== Confirm? Y / N ===");
        if (confirm.toUpperCase().equals("Y")) {
            return true;
        } else {
            return false;
        }
    }

    public void displayAddOrderSuccessBanner(Order order) {
        io.print("\n=== Order Added Successfully! ===");
        io.print("\nOrder Number: " + order.getOrderNumber());
        io.readString("\n=== Hit ENTER to Return to Main Menu ===");
    }

    public void displayEditOrderSuccessBanner() {
        io.print("\n=== Order Edited Successfully! ===");
        io.readString("\n=== Hit ENTER to Return to Main Menu ===");

    }

    public void displayRemoveOrderBanner() {
        io.print("\n=== Remove Order ===");
    }

    public void displayRemoveOrderSuccessBanner() {
        io.print("\n=== Order Removed Successfully! ===");
        io.readString("\n=== Hit ENTER to Return to Main Menu ===");
    }

    public void displayExportDataSuccessBanner() {
        io.print("\n=== Data Exported! ===");
        io.readString("\n=== Hit ENTER to Return to Main Menu ===");
    }

    public void displayExitMessage() {
        io.print("\n=== Have a Great Day! ===");
    }

    public void displayErrorMessage(String message) {
        io.print("\n" + message);
    }

    public void displayUnknownCommandMessage() {
        io.print("\n=== UNKNOWN COMMAND! ===");
    }
}
