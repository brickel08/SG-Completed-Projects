/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.data.LocationDaoDB.LocationMapper;
import com.sg.superherosightings.data.PowerDaoDB.PowerMapper;
import com.sg.superherosightings.data.SuperDaoDB.SuperMapper;
import com.sg.superherosightings.models.Location;
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
public class OrganizationDaoDB implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Organization getOrganizationById(int id) {
        try {
            final String GET_ORGANIZATION_BY_ID = "SELECT * FROM organization WHERE id = ?";
            Organization org = jdbc.queryForObject(GET_ORGANIZATION_BY_ID, new OrganizationMapper(), id);
            org.setLocation(getLocationForOrganization(id));
            org.setSupers(getSupersForOrganization(id));

            return org;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Location getLocationForOrganization(int id) {
        final String SELECT_LOCATION_FOR_ORG = "SELECT l.* FROM location l "
                + "JOIN organization o ON o.locationId = l.id WHERE o.id = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_ORG, new LocationMapper(), id);
    }

    private List<Super> getSupersForOrganization(int id) {
        final String SELECT_SUPERS_FOR_ORG = "SELECT s.* FROM `super` s "
                + "JOIN super_organization so ON so.superId = s.id WHERE so.organizationId = ?";
        List<Super> supers = jdbc.query(SELECT_SUPERS_FOR_ORG, new SuperMapper(), id);
        associateSuperAndPower(supers);
        return supers;
    }

    private Power getPowerForSuper(int id) {
        final String SELECT_POWER_FOR_SUPER = "SELECT p.* FROM power p "
                + "JOIN `super` s ON s.powerId = p.id WHERE s.id = ?";
        return jdbc.queryForObject(SELECT_POWER_FOR_SUPER, new PowerMapper(), id);
    }

    private void associateSuperAndPower(List<Super> supers) {
        for (Super supe : supers) {
            supe.setPower(getPowerForSuper(supe.getId()));
        }
    }

    @Override
    @Transactional
    public List<Organization> getAllOrganizations() {
        final String GET_ALL_ORGANIZATIONS = "SELECT * FROM organization";
        List<Organization> orgs = jdbc.query(GET_ALL_ORGANIZATIONS, new OrganizationMapper());
        associateSupersAndLocation(orgs);
        return orgs;
    }

    private void associateSupersAndLocation(List<Organization> orgs) {
        for (Organization org : orgs) {
            org.setLocation(getLocationForOrganization(org.getId()));
            org.setSupers(getSupersForOrganization(org.getId()));
        }
    }

    @Override
    @Transactional
    public Organization addOrganization(Organization org) {
        final String INSERT_ORG = "INSERT INTO organization(name, description, phone, email, locationId, pic) "
                + "VALUES(?,?,?,?,?,?)";
        jdbc.update(INSERT_ORG,
                org.getName(),
                org.getDescription(),
                org.getPhone(),
                org.getEmail(),
                org.getLocation().getId(),
                org.getPic());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        org.setId(newId);
        insertSuperOrganization(org);
        return org;
    }

    private void insertSuperOrganization(Organization org) {
        final String INSERT_ORG_SUPER = "INSERT INTO super_organization(superId, organizationId) VALUES(?,?)";
        for (Super supe : org.getSupers()) {
            jdbc.update(INSERT_ORG_SUPER, supe.getId(), org.getId());
        }
    }

    @Override
    @Transactional
    public void updateOrganization(Organization org) {
        final String UPDATE_ORG = "UPDATE organization SET name = ?, description = ?, phone = ?, email = ?, locationId = ?, pic = ? "
                + "WHERE id = ?";
        jdbc.update(UPDATE_ORG,
                org.getName(),
                org.getDescription(),
                org.getPhone(),
                org.getEmail(),
                org.getLocation().getId(),
                org.getPic(),
                org.getId());
        final String DELETE_ORG_SUPER = "DELETE FROM super_organization WHERE organizationId = ?";
        jdbc.update(DELETE_ORG_SUPER, org.getId());
        insertSuperOrganization(org);
    }

    @Override
    @Transactional
    public void deleteOrganizationById(int id) {
        final String DELETE_ORG_LOCATION = "DELETE FROM super_organization WHERE organizationId = ?";
        jdbc.update(DELETE_ORG_LOCATION, id);

        final String DELETE_ORG = "DELETE FROM organization WHERE id = ?";
        jdbc.update(DELETE_ORG, id);
    }

    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization org = new Organization();
            org.setId(rs.getInt("id"));
            org.setName(rs.getString("name"));
            org.setDescription(rs.getString("description"));
            org.setPhone(rs.getString("phone"));
            org.setEmail(rs.getString("email"));
            org.setPic(rs.getString("pic"));

            return org;
        }
    }
}

