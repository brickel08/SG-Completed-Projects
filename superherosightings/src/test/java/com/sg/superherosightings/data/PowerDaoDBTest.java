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
public class PowerDaoDBTest {

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

    public PowerDaoDBTest() {
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
     * Test of getPowerById method, of class PowerDaoDB.
     */
    @Test
    public void testAddGetPowerById() {
        Power pow = new Power();
        pow.setName("test name");
        pow.setElement("test element");
        pow.setDescription("test description");
        pow = powerDao.addPower(pow);

        Power fromDao = powerDao.getPowerById(pow.getId());

        assertEquals(pow, fromDao);
    }

    /**
     * Test of getAllPowers method, of class PowerDaoDB.
     */
    @Test
    public void testGetAllPowers() {
        Power pow = new Power();
        pow.setName("test name");
        pow.setElement("test element");
        pow.setDescription("test description");
        pow = powerDao.addPower(pow);

        Power pow2 = new Power();
        pow2.setName("test name");
        pow2.setElement("test element");
        pow2.setDescription("test description");
        pow2 = powerDao.addPower(pow2);

        List<Power> powers = powerDao.getAllPowers();

        assertEquals(2, powers.size());
        assertTrue(powers.contains(pow));
        assertTrue(powers.contains(pow2));

    }

    /**
     * Test of updatePower method, of class PowerDaoDB.
     */
    @Test
    public void testUpdatePower() {
        Power pow = new Power();
        pow.setName("test name");
        pow.setElement("test element");
        pow.setDescription("test description");
        pow = powerDao.addPower(pow);

        Power fromDao = powerDao.getPowerById(pow.getId());
        assertEquals(fromDao, pow);

        pow.setElement("new test element");
        powerDao.updatePower(pow);

        assertNotEquals(pow, fromDao);

        fromDao = powerDao.getPowerById(pow.getId());

        assertEquals(pow, fromDao);
    }

    /**
     * Test of deletePowerById method, of class PowerDaoDB.
     */
    @Test
    public void testDeletePowerById() {
        Power pow = new Power();
        pow.setName("test name");
        pow.setElement("test element");
        pow.setDescription("test description");
        pow = powerDao.addPower(pow);

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

        Power fromDao = powerDao.getPowerById(pow.getId());
        assertEquals(pow, fromDao);

        powerDao.deletePowerById(pow.getId());

        fromDao = powerDao.getPowerById(pow.getId());
        assertNull(fromDao);

    }

}
