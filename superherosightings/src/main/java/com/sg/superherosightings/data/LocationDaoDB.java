/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.models.Location;
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
public class LocationDaoDB implements LocationDao {
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Location getLocationById(int id) {
        try {
            final String GET_LOCATION_BY_ID = "SELECT * FROM location WHERE id = ?";
            return jdbc.queryForObject(GET_LOCATION_BY_ID, new LocationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        final String GET_ALL_LOCATIONS = "SELECT * FROM location";
        return jdbc.query(GET_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String INSERT_LOCATION = "INSERT INTO location(name, streetNumber, streetName, city, state, zip, description, latitude, longitude, pic) " + "VALUES(?,?,?,?,?,?,?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                location.getName(),
                location.getStreetNumber(),
                location.getStreetName(),
                location.getCity(),
                location.getState(),
                location.getZip(),
                location.getDescription(),
                location.getLatitude(),
                location.getLongitude(),
                location.getPic());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newId);
        return location;
    }

    @Override
    public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE location SET name = ?, streetNumber = ?, streetName = ?, city = ?, " +
                "state = ?, zip = ?, description = ?, latitude = ?, longitude = ?, pic = ? WHERE id = ?";
        jdbc.update(UPDATE_LOCATION,
                location.getName(),
                location.getStreetNumber(),
                location.getStreetName(),
                location.getCity(),
                location.getState(),
                location.getZip(),
                location.getDescription(),
                location.getLatitude(),
                location.getLongitude(),
                location.getPic(),
                location.getId());
    }

    @Override
    @Transactional
    public void deleteLocationById(int id) {
        final String DELETE_SUPER_ORG = "DELETE so.* FROM super_organization so "
                + "JOIN organization o ON so.organizationId = o.Id WHERE o.locationId = ?";
        jdbc.update(DELETE_SUPER_ORG, id);
        
        final String DELETE_SIGHTING_LOCATION = "DELETE FROM sighting WHERE locationId = ?";
        jdbc.update(DELETE_SIGHTING_LOCATION, id);
        
        final String DELETE_ORGANIZATION_LOCATION = "DELETE FROM organization WHERE locationId = ?";
        jdbc.update(DELETE_ORGANIZATION_LOCATION, id);
        
        final String DELETE_LOCATION = "DELETE FROM location WHERE id = ?";
        jdbc.update(DELETE_LOCATION, id);
    }
    
    public static final class LocationMapper implements RowMapper<Location> {
        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setId(rs.getInt("id"));
            location.setName(rs.getString("name"));
            location.setStreetNumber(rs.getString("streetNumber"));
            location.setStreetName(rs.getString("streetName"));
            location.setCity(rs.getString("city"));
            location.setState(rs.getString("state"));
            location.setZip(rs.getString("zip"));
            location.setDescription(rs.getString("description"));
            location.setLatitude(rs.getBigDecimal("latitude").stripTrailingZeros());
            location.setLongitude(rs.getBigDecimal("longitude").stripTrailingZeros());
            location.setPic(rs.getString("pic"));
            
            return location;
        }
    }
    
}
