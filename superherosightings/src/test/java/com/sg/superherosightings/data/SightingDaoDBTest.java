/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.models.Location;
import com.sg.superherosightings.models.Organization;
import com.sg.superherosightings.models.Power;
import com.sg.superherosightings.models.Sighting;
import com.sg.superherosightings.models.Super;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author benrickel
 */
@SpringBootTest
public class SightingDaoDBTest {

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    PowerDao powerDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    SuperDao superDao;

    public SightingDaoDBTest() {
    }

    @BeforeEach
    public void setUp() {
        List<Location> locations = locationDao.getAllLocations();
        for (Location loc : locations) {
            locationDao.deleteLocationById(loc.getId());
        }

        List<Organization> orgs = organizationDao.getAllOrganizations();
        for (Organization org : orgs) {
            organizationDao.deleteOrganizationById(org.getId());
        }

        List<Power> powers = powerDao.getAllPowers();
        for (Power power : powers) {
            powerDao.deletePowerById(power.getId());
        }

        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDao.deleteSightingById(sighting.getId());
        }

        List<Super> supers = superDao.getAllSupers();
        for (Super supe : supers) {
            superDao.deleteSuperById(supe.getId());
        }
    }

    /**
     * Test of getSightingById method, of class SightingDaoDB.
     */
    @Test
    public void testAddGetSightingById() {
        Location loc = new Location();
        loc.setName("test loc name");
        loc.setStreetNumber("test street number");
        loc.setStreetName("test street name");
        loc.setCity("test city");
        loc.setState("XX");
        loc.setZip("11111");
        loc.setDescription("test description");
        loc.setLatitude(new BigDecimal("12.34"));
        loc.setLongitude(new BigDecimal("123.45"));
        loc.setPic("test loc pic");
        loc = locationDao.addLocation(loc);

        Power pow = new Power();
        pow.setName("test name");
        pow.setElement("test element");
        pow.setDescription("test description");
        pow = powerDao.addPower(pow);

        Super sup = new Super();
        sup.setName("test name");
        sup.setMorality("villain");
        sup.setDescription("test super description");
        sup.setPower(pow);
        sup.setPic("test pic");
        sup = superDao.addSuper(sup);

        Sighting sight = new Sighting();
        sight.setLocation(loc);
        sight.setSupe(sup);
        sight.setDate(LocalDate.now().withYear(2021));
        sight = sightingDao.addSighting(sight);

        Sighting fromDao = sightingDao.getSightingById(sight.getId());

        assertEquals(sight, fromDao);
    }

    /**
     * Test of getAllSightings method, of class SightingDaoDB.
     */
    @Test
    public void testGetAllSightings() {
        Location loc = new Location();
        loc.setName("test loc name");
        loc.setStreetNumber("test street number");
        loc.setStreetName("test street name");
        loc.setCity("test city");
        loc.setState("XX");
        loc.setZip("11111");
        loc.setDescription("test description");
        loc.setLatitude(new BigDecimal("12.34"));
        loc.setLongitude(new BigDecimal("123.45"));
        loc.setPic("test loc pic");
        loc = locationDao.addLocation(loc);

        Power pow = new Power();
        pow.setName("test name");
        pow.setElement("test element");
        pow.setDescription("test description");
        pow = powerDao.addPower(pow);

        Super sup = new Super();
        sup.setName("test name");
        sup.setMorality("villain");
        sup.setDescription("test super description");
        sup.setPower(pow);
        sup.setPic("test pic");
        sup = superDao.addSuper(sup);

        Sighting sight = new Sighting();
        sight.setLocation(loc);
        sight.setSupe(sup);
        sight.setDate(LocalDate.now().withYear(2021));
        sight = sightingDao.addSighting(sight);

        // sighting 2
        Sighting sight2 = new Sighting();
        sight2.setLocation(loc);
        sight2.setSupe(sup);
        sight2.setDate(LocalDate.now().withYear(2021));
        sight2 = sightingDao.addSighting(sight2);

        List<Sighting> sightings = sightingDao.getAllSightings();

        assertTrue(sightings.contains(sight));
        assertTrue(sightings.contains(sight2));
        assertEquals(2, sightings.size());
    }

    /**
     * Test of updateSighting method, of class SightingDaoDB.
     */
    @Test
    public void testUpdateSighting() {
        Location loc = new Location();
        loc.setName("test loc name");
        loc.setStreetNumber("test street number");
        loc.setStreetName("test street name");
        loc.setCity("test city");
        loc.setState("XX");
        loc.setZip("11111");
        loc.setDescription("test description");
        loc.setLatitude(new BigDecimal("12.34"));
        loc.setLongitude(new BigDecimal("123.45"));
        loc.setPic("test loc pic");
        loc = locationDao.addLocation(loc);

        Power pow = new Power();
        pow.setName("test name");
        pow.setElement("test element");
        pow.setDescription("test description");
        pow = powerDao.addPower(pow);

        Super sup = new Super();
        sup.setName("test name");
        sup.setMorality("villain");
        sup.setDescription("test super description");
        sup.setPower(pow);
        sup.setPic("test pic");
        sup = superDao.addSuper(sup);

        Sighting sight = new Sighting();
        sight.setLocation(loc);
        sight.setSupe(sup);
        sight.setDate(LocalDate.now().withYear(2021));
        sight = sightingDao.addSighting(sight);

        Location loc2 = new Location();
        loc2.setName("test loc name 2");
        loc2.setStreetNumber("test street number 2");
        loc2.setStreetName("test street name 2");
        loc2.setCity("test city 2");
        loc2.setState("YY");
        loc2.setZip("22222");
        loc2.setDescription("test description 2");
        loc2.setLatitude(new BigDecimal("43.21"));
        loc2.setLongitude(new BigDecimal("543.21"));
        loc2.setPic("test loc pic 2");
        loc2 = locationDao.addLocation(loc2);

        Sighting fromDao = sightingDao.getSightingById(sight.getId());
        assertEquals(sight, fromDao);

        sight.setLocation(loc2);

        sightingDao.updateSighting(sight);

        assertNotEquals(sight, fromDao);

        fromDao = sightingDao.getSightingById(sight.getId());
        assertEquals(fromDao, sight);

    }

    /**
     * Test of deleteSightingById method, of class SightingDaoDB.
     */
    @Test
    public void testDeleteSightingById() {
        Location loc = new Location();
        loc.setName("test loc name");
        loc.setStreetNumber("test street number");
        loc.setStreetName("test street name");
        loc.setCity("test city");
        loc.setState("XX");
        loc.setZip("11111");
        loc.setDescription("test description");
        loc.setLatitude(new BigDecimal("12.34"));
        loc.setLongitude(new BigDecimal("123.45"));
        loc.setPic("test loc pic");
        loc = locationDao.addLocation(loc);

        Power pow = new Power();
        pow.setName("test name");
        pow.setElement("test element");
        pow.setDescription("test description");
        pow = powerDao.addPower(pow);

        Super sup = new Super();
        sup.setName("test name");
        sup.setMorality("villain");
        sup.setDescription("test super description");
        sup.setPower(pow);
        sup.setPic("test pic");
        sup = superDao.addSuper(sup);

        Sighting sight = new Sighting();
        sight.setLocation(loc);
        sight.setSupe(sup);
        sight.setDate(LocalDate.now().withYear(2021));
        sight = sightingDao.addSighting(sight);

        Sighting fromDao = sightingDao.getSightingById(sight.getId());
        assertEquals(sight, fromDao);

        sightingDao.deleteSightingById(sight.getId());

        fromDao = sightingDao.getSightingById(sight.getId());
        assertNull(fromDao);
    }

    /**
     * Test of getSightingsForSuper method, of class SightingDaoDB.
     */
    @Test
    public void testGetSightingsForSuper() {
        Location loc = new Location();
        loc.setName("test loc name");
        loc.setStreetNumber("test street number");
        loc.setStreetName("test street name");
        loc.setCity("test city");
        loc.setState("XX");
        loc.setZip("11111");
        loc.setDescription("test description");
        loc.setLatitude(new BigDecimal("12.34"));
        loc.setLongitude(new BigDecimal("123.45"));
        loc.setPic("test loc pic");
        loc = locationDao.addLocation(loc);

        Power pow = new Power();
        pow.setName("test name");
        pow.setElement("test element");
        pow.setDescription("test description");
        pow = powerDao.addPower(pow);

        Super sup = new Super();
        sup.setName("test name");
        sup.setMorality("villain");
        sup.setDescription("test super description");
        sup.setPower(pow);
        sup.setPic("test pic");
        sup = superDao.addSuper(sup);

        Sighting sight = new Sighting();
        sight.setLocation(loc);
        sight.setSupe(sup);
        sight.setDate(LocalDate.now().withYear(2021));
        sight = sightingDao.addSighting(sight);

        Location loc2 = new Location();
        loc2.setName("test loc name");
        loc2.setStreetNumber("test street number");
        loc2.setStreetName("test street name");
        loc2.setCity("test city");
        loc2.setState("XX");
        loc2.setZip("11111");
        loc2.setDescription("test description");
        loc2.setLatitude(new BigDecimal("12.34"));
        loc2.setLongitude(new BigDecimal("123.45"));
        loc2.setPic("test loc pic");
        loc2 = locationDao.addLocation(loc2);

        Power pow2 = new Power();
        pow2.setName("test name");
        pow2.setElement("test element");
        pow2.setDescription("test description");
        pow2 = powerDao.addPower(pow2);

        Super sup2 = new Super();
        sup2.setName("test name");
        sup2.setMorality("villain");
        sup2.setDescription("test super description");
        sup2.setPower(pow);
        sup2.setPic("test pic");
        sup2 = superDao.addSuper(sup2);

        Sighting sight2 = new Sighting();
        sight2.setLocation(loc);
        sight2.setSupe(sup);
        sight2.setDate(LocalDate.now().withYear(2021));
        sight2 = sightingDao.addSighting(sight2);

        Sighting sight3 = new Sighting();
        sight3.setLocation(loc2);
        sight3.setSupe(sup2);
        sight.setDate(LocalDate.now().withYear(2021));
        sight3 = sightingDao.addSighting(sight3);

        List<Sighting> sightings = sightingDao.getSightingsForSuper(sup);
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sight));
        assertFalse(sightings.contains(sight3));
        assertTrue(sightings.contains(sight2));
    }

    /**
     * Test of getSightingsForLocation method, of class SightingDaoDB.
     */
    @Test
    public void testGetSightingsForLocation() {
        Location loc = new Location();
        loc.setName("test loc name");
        loc.setStreetNumber("test street number");
        loc.setStreetName("test street name");
        loc.setCity("test city");
        loc.setState("XX");
        loc.setZip("11111");
        loc.setDescription("test description");
        loc.setLatitude(new BigDecimal("12.34"));
        loc.setLongitude(new BigDecimal("123.45"));
        loc.setPic("test loc pic");
        loc = locationDao.addLocation(loc);

        Power pow = new Power();
        pow.setName("test name");
        pow.setElement("test element");
        pow.setDescription("test description");
        pow = powerDao.addPower(pow);

        Super sup = new Super();
        sup.setName("test name");
        sup.setMorality("villain");
        sup.setDescription("test super description");
        sup.setPower(pow);
        sup.setPic("test pic");
        sup = superDao.addSuper(sup);

        Sighting sight = new Sighting();
        sight.setLocation(loc);
        sight.setSupe(sup);
        sight.setDate(LocalDate.now().withYear(2021));
        sight = sightingDao.addSighting(sight);

        Location loc2 = new Location();
        loc2.setName("test loc name");
        loc2.setStreetNumber("test street number");
        loc2.setStreetName("test street name");
        loc2.setCity("test city");
        loc2.setState("XX");
        loc2.setZip("11111");
        loc2.setDescription("test description");
        loc2.setLatitude(new BigDecimal("12.34"));
        loc2.setLongitude(new BigDecimal("123.45"));
        loc2.setPic("test loc pic");
        loc2 = locationDao.addLocation(loc2);

        Power pow2 = new Power();
        pow2.setName("test name");
        pow2.setElement("test element");
        pow2.setDescription("test description");
        pow2 = powerDao.addPower(pow2);

        Super sup2 = new Super();
        sup2.setName("test name");
        sup2.setMorality("villain");
        sup2.setDescription("test super description");
        sup2.setPower(pow);
        sup2.setPic("test pic");
        sup2 = superDao.addSuper(sup2);

        Sighting sight2 = new Sighting();
        sight2.setLocation(loc);
        sight2.setSupe(sup);
        sight2.setDate(LocalDate.now().withYear(2021));
        sight2 = sightingDao.addSighting(sight2);

        Sighting sight3 = new Sighting();
        sight3.setLocation(loc2);
        sight3.setSupe(sup2);
        sight.setDate(LocalDate.now().withYear(2021));
        sight3 = sightingDao.addSighting(sight3);

        List<Sighting> sightings = sightingDao.getSightingsForLocation(loc);
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sight));
        assertTrue(sightings.contains(sight2));
        assertFalse(sightings.contains(sight3));
    }

}
