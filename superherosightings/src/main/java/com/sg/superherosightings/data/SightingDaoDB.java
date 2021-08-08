/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.data.LocationDaoDB.LocationMapper;
import com.sg.superherosightings.data.SuperDaoDB.SuperMapper;
import com.sg.superherosightings.models.Location;
import com.sg.superherosightings.models.Power;
import com.sg.superherosightings.models.Sighting;
import com.sg.superherosightings.models.Super;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
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
public class SightingDaoDB implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Sighting getSightingById(int id) {
        try {
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM sighting WHERE id = ?";
            Sighting sight = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(), id);
            sight.setLocation(getLocationForSighting(id));
            Super sup = getSuperForSighting(id);
            sup.setPower(getPowerForSuper(sup.getId()));
            sight.setSupe(sup);
            return sight;

        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Location getLocationForSighting(int id) {
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l.* FROM location l JOIN sighting s ON s.locationId = l.id WHERE s.id = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationMapper(), id);
    }

    private Super getSuperForSighting(int id) {
        final String SELECT_SUPER_FOR_SIGHTING = "SELECT su.* FROM `super` su JOIN sighting s ON s.superId = su.id WHERE s.id = ?";
        return jdbc.queryForObject(SELECT_SUPER_FOR_SIGHTING, new SuperMapper(), id);
    }

    private Power getPowerForSuper(int id) {
        final String SELECT_POWER_FOR_SUPER = "SELECT p.* FROM power p "
                + "JOIN `super` s ON s.powerId = p.id WHERE s.id = ?";
        return jdbc.queryForObject(SELECT_POWER_FOR_SUPER, new PowerDaoDB.PowerMapper(), id);
    }

    @Override
    @Transactional
    public List<Sighting> getAllSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sighting";
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
        associateSupersAndLocations(sightings);

        return sightings;
    }

    private void associateSupersAndLocations(List<Sighting> sightings) {
        for (Sighting sight : sightings) {
            sight.setLocation(getLocationForSighting(sight.getId()));
            Super supe = getSuperForSighting(sight.getId());
            supe.setPower(getPowerForSuper(supe.getId()));
            sight.setSupe(supe);
        }
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO sighting(locationId, superId, date) VALUES(?,?,?)";
        jdbc.update(INSERT_SIGHTING, sighting.getLocation().getId(), sighting.getSupe().getId(), sighting.getDate());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
        return sighting;
    }

    @Override
    @Transactional
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE sighting SET locationId = ?, superId = ?, date = ? WHERE id = ?";
        jdbc.update(UPDATE_SIGHTING,
                sighting.getLocation().getId(),
                sighting.getSupe().getId(),
                sighting.getDate(),
                sighting.getId());
    }

    @Override
    @Transactional
    public void deleteSightingById(int id) {
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE id = ?";
        jdbc.update(DELETE_SIGHTING, id);
    }

    @Override
    @Transactional
    public List<Sighting> getSightingsForSuper(Super supe) {
        final String SELECT_SIGHTINGS_FOR_SUPER = "SELECT * FROM sighting WHERE superId = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_SUPER, new SightingMapper(), supe.getId());
        associateSupersAndLocations(sightings);
        return sightings;
    }

    @Override
    @Transactional
    public List<Sighting> getAllOrderedSightings() {
        final String SELECT_ORDERED_SIGHTINGS = "SELECT * FROM sighting ORDER BY date DESC LIMIT 10";
        List<Sighting> sightings = jdbc.query(SELECT_ORDERED_SIGHTINGS, new SightingMapper());
        associateSupersAndLocations(sightings);
        
        //sightings = sightings.stream().limit(10).collect(Collectors.toList());
        
        return sightings;
    }

    @Override
    @Transactional
    public List<Sighting> getSightingsForLocation(Location location) {
        final String SELECT_SIGHTINGS_FOR_LOCATION = "SELECT * FROM sighting WHERE locationId = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_LOCATION, new SightingMapper(), location.getId());
        associateSupersAndLocations(sightings);
        return sightings;
    }

    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sight = new Sighting();
            sight.setId(rs.getInt("id"));
            sight.setDate(rs.getDate("date").toLocalDate());
            return sight;
        }
    }

}
