/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.data.OrganizationDaoDB.OrganizationMapper;
import com.sg.superherosightings.data.PowerDaoDB.PowerMapper;
import com.sg.superherosightings.models.Organization;
import com.sg.superherosightings.models.Power;
import com.sg.superherosightings.models.Super;
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
public class SuperDaoDB implements SuperDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Super getSuperById(int id) {
        try {
            final String SELECT_SUPER_BY_ID = "SELECT * FROM `super` WHERE id = ?";
            Super supe = jdbc.queryForObject(SELECT_SUPER_BY_ID, new SuperMapper(), id);
            supe.setPower(getPowerForSuper(id));
            return supe;

        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Power getPowerForSuper(int id) {
        final String SELECT_POWER_FOR_SUPER = "SELECT p.* FROM power p "
                + "JOIN `super` s ON s.powerId = p.id WHERE s.id = ?";
        return jdbc.queryForObject(SELECT_POWER_FOR_SUPER, new PowerMapper(), id);
    }

    @Override
    @Transactional
    public List<Super> getAllSupers() {
        final String SELECT_ALL_SUPERS = "SELECT * FROM `super`";
        List<Super> supers = jdbc.query(SELECT_ALL_SUPERS, new SuperMapper());
        associatePowerAndOrganizations(supers);
        return supers;
    }

    private void associatePowerAndOrganizations(List<Super> supers) {
        for (Super supe : supers) {
            supe.setPower(getPowerForSuper(supe.getId()));
        }
    }

    @Override
    @Transactional
    public Super addSuper(Super supe) {
        final String INSERT_SUPER = "INSERT INTO `super`(name, morality, description, powerId, pic) VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_SUPER,
                supe.getName(),
                supe.getMorality(),
                supe.getDescription(),
                supe.getPower().getId(),
                supe.getPic());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        supe.setId(newId);
        supe.setPower(getPowerForSuper(supe.getId()));
        return supe;

    }

    @Override
    @Transactional
    public void updateSuper(Super supe) {
        final String UPDATE_SUPER = "UPDATE `super` SET name = ?, morality = ?, description = ?, powerId = ?, pic = ? WHERE id = ?";
        jdbc.update(UPDATE_SUPER, supe.getName(), supe.getMorality(), supe.getDescription(), 
               supe.getPower().getId(), supe.getPic(), supe.getId());

        final String DELETE_SUPER_ORG = "DELETE FROM super_organization WHERE superId = ?";
        jdbc.update(DELETE_SUPER_ORG, supe.getId());
    }

    @Override
    @Transactional
    public void deleteSuperById(int id) {
        final String DELETE_SUPER_ORG = "DELETE FROM super_organization WHERE superId = ?";
        jdbc.update(DELETE_SUPER_ORG, id);

        final String DELETE_SUPER_SIGHTING = "DELETE FROM sighting si WHERE superId = ?";
        jdbc.update(DELETE_SUPER_SIGHTING, id);

        final String DELETE_SUPER = "DELETE FROM `super` WHERE id = ?";
        jdbc.update(DELETE_SUPER, id);
    }

    public static final class SuperMapper implements RowMapper<Super> {

        @Override
        public Super mapRow(ResultSet rs, int index) throws SQLException {
            Super supe = new Super();
            supe.setId(rs.getInt("id"));
            supe.setName(rs.getString("name"));
            supe.setMorality(rs.getString("morality"));
            supe.setDescription(rs.getString("description"));
            supe.setPic(rs.getString("pic"));
            
            return supe;
        }
    }

}
