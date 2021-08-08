/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.ExportDataDao;
import com.sg.flooringmastery.dao.OrderDao;
import com.sg.flooringmastery.dao.PersistenceException;
import com.sg.flooringmastery.dao.ProductDao;
import com.sg.flooringmastery.dao.TaxDao;
import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.model.Product;
import com.sg.flooringmastery.model.Tax;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.OptionalInt;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author benrickel
 */
@Component
public class ServiceLayerFileImpl implements ServiceLayer {

    OrderDao orderDao;
    TaxDao taxDao;
    ProductDao productDao;
    ExportDataDao exportDataDao;

    @Autowired
    public ServiceLayerFileImpl(OrderDao orderDao, TaxDao taxDao, ProductDao productDao, ExportDataDao exportDataDao) {
        this.orderDao = orderDao;
        this.taxDao = taxDao;
        this.productDao = productDao;
        this.exportDataDao = exportDataDao;
    }

    @Override
    public Order addOrder(Order order) throws PersistenceException {
        try {
            order.setOrderNumber(createOrderNumber());
            orderDao.addOrder(order.getOrderNumber(), order);
            return order;
        } catch (IOException e) {
            throw new PersistenceException("\n=== Could not persist data! ===", e);
        }
    }

    @Override
    public Order editOrder(Order order) throws PersistenceException, NoSuchOrderException {
        try {
            order = orderDao.editOrder(order);
            return order;
        } catch (IOException e) {
            throw new PersistenceException("\n=== Could not persist data! ===", e);
        }
    }

    @Override
    public Order removeOrder(Order order) throws InvalidDateException, InvalidDataEntryException, PersistenceException, NoSuchOrderException {
        try {
            validateOrder(order);
            Order removedOrder = orderDao.removeOrder(order);
            return removedOrder;
        } catch (IOException e) {
            throw new PersistenceException("\n=== Could not persist data! ===", e);
        }
    }

    @Override
    public Collection<Order> getOrdersByDate(LocalDate orderDate) throws PersistenceException, NoSuchOrderException {
        try {
            Collection<Order> orders = orderDao.getOrdersByDate(orderDate);
            return orders;
        } catch (PersistenceException e) {
            throw new PersistenceException("\n=== Could not persist data! ===", e);
        }
    }

    @Override
    public Order getOrderByDateAndNumber(Order order) throws PersistenceException, NoSuchOrderException, InvalidDateException, InvalidDataEntryException {
        try {

            order = orderDao.getOrderByDateAndNumber(order);
            if (order == null) {
                throw new NoSuchOrderException("\n=== Could not find Order!!! ===");
            }
            return order;

        } catch (PersistenceException e) {
            throw new NoSuchOrderException("\n=== Could not find Order! ===", e);
        }
    }

    @Override
    public List<Order> getAllOrders() throws PersistenceException {
        try {
            List<Order> orders = orderDao.getAllOrders();
            return orders;
        } catch (PersistenceException e) {
            throw new PersistenceException("\n=== Could not persist data! ===", e);
        }
    }

    @Override
    public List<Product> getAllProducts() throws PersistenceException {
        try {
            return productDao.getAllProducts();
        } catch (NoSuchOrderException e) {
            throw new PersistenceException("\n=== Could not persist data! ===", e);
        }
    }

    @Override
    public List<Tax> getAllStateTaxInfo() throws PersistenceException {
        try {
            return taxDao.getAllStatesTaxInfo();
        } catch (PersistenceException e) {
            throw new PersistenceException("\n=== Could not persist data! ===", e);
        }
    }

    @Override
    public void exportData() throws PersistenceException, NoSuchOrderException {
        try {
            List<Order> ordersList = orderDao.getAllOrders();
            exportDataDao.writeData(ordersList);
        } catch (IOException e) {
            throw new PersistenceException("\n=== Could not persist data! ===", e);
        }
    }

    @Override
    public Order calculateTotalCost(Order order) throws InvalidDateException, InvalidDataEntryException, NoSuchOrderException, InvalidProductException, InvalidNameException, InvalidStateException, PersistenceException {
        if (order.getOrderNumber() == 0) {
            try {

                validateAddOrder(order);

                Tax tax = taxDao.getStateTaxInfo(order.getTaxInfo().getStateAbbreviation());
                Product product = productDao.getProductInfo(order.getProductInfo().getProductType());
                order.getTaxInfo().setStateName(tax.getStateName());
                order.setTaxInfo(tax);
                order.setProductInfo(product);

                // MATERIAL COST
                BigDecimal area = order.getArea();
                BigDecimal costPerSquareFoot = product.getCostPerSquareFoot();
                order.getProductInfo().setCostPerSquareFoot(costPerSquareFoot);
                BigDecimal materialCost = area.multiply(costPerSquareFoot);
                materialCost.setScale(2, RoundingMode.HALF_UP);
                order.setMaterialCost(materialCost);

                // LABOR COST
                BigDecimal laborCostPerSquareFoot = product.getLaborCostPerSquareFoot();
                order.getProductInfo().setLaborCostPerSquareFoot(laborCostPerSquareFoot);
                BigDecimal laborCost = area.multiply(laborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
                order.setLaborCost(laborCost);

                // TAX TOTAL
                BigDecimal taxRate = tax.getTaxRate();
                order.getTaxInfo().setTaxRate(taxRate);
                BigDecimal taxTotal = (materialCost.add(laborCost).multiply(taxRate.divide(new BigDecimal("100"))));
                order.setTaxTotal(taxTotal.setScale(2, RoundingMode.HALF_UP));

                // GRAND TOTAL
                BigDecimal grandTotal = materialCost.add(laborCost).add(taxTotal);
                order.setGrandTotal(grandTotal.setScale(2, RoundingMode.HALF_UP));

                return order;

            } catch (PersistenceException e) {
                throw new PersistenceException("\n=== Could not persist data! ===", e);
            }
        } else if (order.getOrderNumber() > 0) {
            try {
                validateEditOrder(order);

                Tax tax = taxDao.getStateTaxInfo(order.getTaxInfo().getStateAbbreviation());
                Product product = productDao.getProductInfo(order.getProductInfo().getProductType());

                order.setTaxInfo(tax);
                order.getTaxInfo().setStateName(tax.getStateName());
                order.setProductInfo(product);

                // MATERIAL COST
                BigDecimal area = order.getArea();
                BigDecimal costPerSquareFoot = product.getCostPerSquareFoot();
                order.getProductInfo().setCostPerSquareFoot(costPerSquareFoot);
                BigDecimal materialCost = area.multiply(costPerSquareFoot);
                order.setMaterialCost(materialCost);

                // LABOR COST
                BigDecimal laborCostPerSquareFoot = product.getLaborCostPerSquareFoot();
                order.getProductInfo().setLaborCostPerSquareFoot(laborCostPerSquareFoot);
                BigDecimal laborCost = area.multiply(laborCostPerSquareFoot);
                order.setLaborCost(laborCost);

                // TAX TOTAL
                BigDecimal taxRate = tax.getTaxRate();
                order.getTaxInfo().setTaxRate(taxRate);
                BigDecimal taxTotal = (materialCost.add(laborCost).multiply(taxRate.divide(new BigDecimal("100"))));
                order.setTaxTotal(taxTotal.setScale(2, RoundingMode.HALF_UP));

                // GRAND TOTAL
                BigDecimal grandTotal = materialCost.add(laborCost).add(taxTotal);
                order.setGrandTotal(grandTotal.setScale(2, RoundingMode.HALF_UP));

                return order;

            } catch (PersistenceException e) {
                throw new PersistenceException("\n=== Could not persist data! ===", e);
            }
        } else {
            return null;
        }
    }

    private Order validateAddOrder(Order order) throws InvalidDateException, InvalidDataEntryException, NoSuchOrderException, InvalidProductException, InvalidNameException, InvalidStateException, PersistenceException {

        String regex = "^[A-Za-z0-9,.\\s]+$";
        Pattern pt = Pattern.compile(regex);
        String stateRegex = "^[A-Z]+$";
        Pattern stpt = Pattern.compile(stateRegex);

        if (order.getOrderDate().isBefore(LocalDate.now())
                || order.getOrderDate() == null
                || order.getOrderDate().toString().isEmpty() == true) {
            throw new InvalidDateException("\n=== Please Enter a Valid Date! ===");
        } else if (order.getCustomerName() == null
                || order.getCustomerName().trim().length() == 0
                || !pt.matcher(order.getCustomerName()).matches()) {
            throw new InvalidNameException("\n=== Please Enter a Valid Name! ===");
        } else if (productDao.isValidProduct(order.getProductInfo().getProductType()) == false
                || order.getProductInfo().getProductType().trim().length() == 0) {
            throw new InvalidDataEntryException("\n=== Please Enter a Valid Product! ===");
        } else if (order.getTaxInfo().getStateAbbreviation() == null
                || order.getTaxInfo().getStateAbbreviation().trim().length() == 0
                || taxDao.isValidState(order.getTaxInfo().getStateAbbreviation()) == false
                || !stpt.matcher(stateRegex).matches() == false) {
            throw new InvalidStateException("\n=== Please Enter a Valid State! ===");
        } else if (Integer.parseInt(order.getArea().toString()) < 100) {
            throw new InvalidDataEntryException("\n=== Area cannot be less than 100 sq ft! ===");
        } else {
            return order;
        }
    }

    private Order validateEditOrder(Order order) throws InvalidDateException, InvalidDataEntryException, NoSuchOrderException, InvalidProductException, InvalidNameException, InvalidStateException, PersistenceException, NoSuchOrderException {
        try {

            Order oldOrder = orderDao.getOrderByDateAndNumber(order);

            String regex = "^[A-Za-z0-9,.\\s]+$";
            Pattern pt = Pattern.compile(regex);
            String stateRegex = "^[A-Z]+$";
            Pattern stpt = Pattern.compile(stateRegex);

            if (order.getCustomerName().equals("") == true
                    || order.getCustomerName().trim().length() == 0) {
                order.setCustomerName(oldOrder.getCustomerName());
            } else if (!pt.matcher(order.getCustomerName()).matches()
                    || order.getCustomerName() == null) {
                throw new InvalidNameException("\n=== Please enter a valid name! ===");
            } else if (order.getTaxInfo().getStateAbbreviation().equals("") == true
                    || order.getTaxInfo().getStateAbbreviation().trim().length() == 0) {
                order.getTaxInfo().setStateAbbreviation(oldOrder.getTaxInfo().getStateAbbreviation());
            } else if (order.getTaxInfo().getStateAbbreviation() == null
                    || !stpt.matcher(order.getTaxInfo().getStateAbbreviation()).matches()
                    || taxDao.isValidState(order.getTaxInfo().getStateAbbreviation()) == false) {
                throw new InvalidStateException("\n=== Please enter a valid State! ===");
            } else if (order.getProductInfo().getProductType().equals("") == true
                    || order.getProductInfo().getProductType().trim().length() == 0) {
                order.getProductInfo().setProductType(oldOrder.getProductInfo().getProductType());
            } else if (order.getProductInfo().getProductType() == null
                    || productDao.isValidProduct(order.getProductInfo().getProductType()) == false) {
                throw new InvalidProductException("\n=== Please enter a valid Product! ===");
            } else if (order.getArea().toString().isEmpty() == true) {
                order.setArea(oldOrder.getArea());
            } else if (Integer.parseInt(order.getArea().toString()) < 100
                    || order.getArea() == null) {
                throw new InvalidDataEntryException("\n=== Area cannot be less than 100 sq ft! ===");
            }
            return order;
        } catch (PersistenceException e) {
            throw new PersistenceException("\n=== Could not persist data! ===", e);
        }
    }

    private Order validateOrder(Order order) throws InvalidDateException, InvalidDataEntryException, PersistenceException, NoSuchOrderException {

        if (order.getOrderDate().isBefore(LocalDate.now())
                || orderDao.getOrderByDateAndNumber(order) == null
                || order.getOrderDate().toString().isEmpty() == true) {
            throw new InvalidDateException("\n=== Please Enter a Valid Date! ===");
        } else if (order.getOrderNumber() < 0
                || orderDao.getOrderByDateAndNumber(order) == null) {
            throw new InvalidDataEntryException("\n=== Please Enter a Valid Order Number! ===");
        }
        return order;
    }

    private Integer createOrderNumber() throws PersistenceException {
        try {
            OptionalInt max = orderDao.getAllOrders()
                    .stream()
                    .mapToInt((o) -> o.getOrderNumber())
                    .max();
            return max.getAsInt() + 1;
        } catch (PersistenceException e) {
            throw new PersistenceException("\n=== Could not persist data! ===", e);
        }
    }

}
