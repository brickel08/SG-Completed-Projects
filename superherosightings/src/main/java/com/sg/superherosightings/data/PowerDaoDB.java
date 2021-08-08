/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.models.Power;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author benrickel
 */
@Repository
public class PowerDaoDB implements PowerDao {
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Power getPowerById(int id) {
        try {
            final String GET_POWER_BY_ID = "SELECT * FROM `power` WHERE id = ?";
            return jdbc.queryForObject(GET_POWER_BY_ID, new PowerMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Power> getAllPowers() {
        final String GET_ALL_POWERS = "SELECT * FROM `power`";
        return jdbc.query(GET_ALL_POWERS, new PowerMapper());
    }

    @Override
    @Transactional
    public Power addPower(Power power) {
        final String INSERT_POWER = "INSERT INTO `power`(name, element, description) VALUES(?,?,?)";
        jdbc.update(INSERT_POWER,
                power.getName(),
                power.getElement(),
                power.getDescription());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        power.setId(newId);
        return power;
    }

    @Override
    public void updatePower(Power power) {
        final String UPDATE_POWER = "UPDATE `power` SET name = ?, element = ?, description = ? WHERE id = ?";
        jdbc.update(UPDATE_POWER,
                power.getName(),
                power.getElement(),
                power.getDescription(),
                power.getId());
    }

    @Override
    @Transactional
    public void deletePowerById(int id) {      
        final String DELETE_SUPER_ORG = "DELETE so.* FROM super_organization so "
                + "JOIN `super` s ON so.superId = s.id WHERE s.powerId = ?";
        jdbc.update(DELETE_SUPER_ORG, id);
        
        final String DELETE_SUPER_SIGHTING = "DELETE si.* FROM sighting si "
                + "JOIN `super` s ON si.superId = s.id WHERE s.powerId = ?";
        jdbc.update(DELETE_SUPER_SIGHTING, id);
        
        final String DELETE_SUPER_POWER = "DELETE FROM `super` WHERE powerId = ?";
        jdbc.update(DELETE_SUPER_POWER, id);
        
        final String DELETE_POWER = "DELETE FROM `power` WHERE id = ?";
        jdbc.update(DELETE_POWER, id);
    }
    
    public static final class PowerMapper implements RowMapper<Power> {
        @Override
        public Power mapRow(ResultSet rs, int index) throws SQLException {
            Power power = new Power();
            power.setId(rs.getInt("id"));
            power.setName(rs.getString("name"));
            power.setElement(rs.getString("element"));
            power.setDescription(rs.getString("description"));
            
            return power;
        }
    }
    
}
