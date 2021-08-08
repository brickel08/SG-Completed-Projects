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
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class LocationDaoDBTest {

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

    public LocationDaoDBTest() {
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
     * Test of getLocationById method, of class LocationDaoDB.
     */
    @Test
    public void testAddGetLocationById() {
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

        Location fromDao = locationDao.getLocationById(loc.getId());
        assertEquals(fromDao, loc);
    }

    /**
     * Test of getAllLocations method, of class LocationDaoDB.
     */
    @Test
    public void testGetAllLocations() {
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

        List<Location> locs = locationDao.getAllLocations();

        assertEquals(2, locs.size());
        assertTrue(locs.contains(loc));
        assertTrue(locs.contains(loc2));

    }

    /**
     * Test of updateLocation method, of class LocationDaoDB.
     */
    @Test
    public void testUpdateLocation() {
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

        Location fromDao = locationDao.getLocationById(loc.getId());
        assertEquals(fromDao, loc);

        loc.setName("updated name");
        locationDao.updateLocation(loc);

        assertNotEquals(loc, fromDao);

        fromDao = locationDao.getLocationById(loc.getId());

        assertEquals(fromDao, loc);

    }

    /**
     * Test of deleteLocationById method, of class LocationDaoDB.
     */
    @Test
    public void testDeleteLocationById() {
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
        sup.setDescription("Yestest super description");
        sup.setPower(pow);
        sup.setPic("test pic");
        sup = superDao.addSuper(sup);

        List<Super> supers = new ArrayList();
        supers = superDao.getAllSupers();

        Organization org = new Organization();
        org.setName("test name");
        org.setDescription("test description");
        org.setPhone("9999999999");
        org.setEmail("test email");
        org.setLocation(loc);
        org.setPic("test organization pic");
        org.setSupers(supers);
        org = organizationDao.addOrganization(org);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now().withYear(0));
        sighting.setLocation(loc);
        sighting.setSupe(sup);
        sighting = sightingDao.addSighting(sighting);

        loc = locationDao.getLocationById(loc.getId());

        Location fromDao = locationDao.getLocationById(loc.getId());
        assertEquals(fromDao, loc);

        locationDao.deleteLocationById(loc.getId());

        fromDao = locationDao.getLocationById(loc.getId());
        assertNull(fromDao);

    }

}
