/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Order;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author benrickel
 */
@Component
public class ExportDataDaoFileImpl implements ExportDataDao {

    public static final String DELIMITER = ",";
    public String DATA_EXPORT_FILE;

    public ExportDataDaoFileImpl() {
        DATA_EXPORT_FILE = "/Users/benrickel/Downloads/Software Guild/online-java-2019-brickel08/Summatives/m5 summative/Flooring Mastery/FlooringMastery/Backup/DataExport.txt/";
    }

    @Autowired
    public ExportDataDaoFileImpl(String DATA_EXPORT_FILE) {
        this.DATA_EXPORT_FILE = DATA_EXPORT_FILE;
    }

    @Override
    public void writeData(List<Order> ordersList) throws PersistenceException, IOException {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(DATA_EXPORT_FILE));

            String header = "OrderNumber,CustomerName,State,TaxRate,"
                    + "ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,"
                    + "MaterialCost,LaborCost,Tax,Total,OrderDate";
            out.println(header);

            for (Order orders : ordersList) {
                String data = marshallData(orders);
                out.println(data);
            }
            out.flush();
            out.close();

        } catch (IOException e) {
            throw new PersistenceException("\n=== Could not export data! ===", e);
        }
    }

    private String marshallData(Order orderData) throws PersistenceException {

        String currentLine = (orderData.getOrderNumber() + DELIMITER
                + orderData.getCustomerName() + DELIMITER
                + orderData.getTaxInfo().getStateAbbreviation() + DELIMITER
                + orderData.getTaxInfo().getTaxRate() + DELIMITER
                + orderData.getProductInfo().getProductType() + DELIMITER
                + orderData.getProductInfo().getCostPerSquareFoot() + DELIMITER
                + orderData.getProductInfo().getLaborCostPerSquareFoot() + DELIMITER
                + orderData.getMaterialCost() + DELIMITER
                + orderData.getLaborCost() + DELIMITER
                + orderData.getTaxTotal() + DELIMITER
                + orderData.getGrandTotal() + DELIMITER
                + orderData.getOrderDate());
        return currentLine;
    }
}
