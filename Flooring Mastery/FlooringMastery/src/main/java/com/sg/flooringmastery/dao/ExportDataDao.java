/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Order;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author benrickel
 */
public interface ExportDataDao {
        
    public void writeData(List<Order> ordersList) throws PersistenceException, IOException;
        
}
