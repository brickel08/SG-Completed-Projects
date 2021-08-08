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
public class OrganizationDaoDBTest {

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

    public OrganizationDaoDBTest() {
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
     * Test of getOrganizationById method, of class OrganizationDaoDB.
     */
    @Test
    public void testAddGetOrganizationById() {
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
        sup.setDescription("test description");
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

        Organization fromDao = organizationDao.getOrganizationById(org.getId());

        assertEquals(org, fromDao);
    }

    /**
     * Test of getAllOrganizations method, of class OrganizationDaoDB.
     */
    @Test
    public void testGetAllOrganizations() {
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
        sup.setDescription("test description");
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

        Organization org2 = new Organization();
        org2.setName("test name");
        org2.setDescription("test description");
        org2.setPhone("9999999999");
        org2.setEmail("test email");
        org2.setLocation(loc);
        org2.setPic("test organization pic");
        org2.setSupers(supers);
        org2 = organizationDao.addOrganization(org2);

        List<Organization> orgs = organizationDao.getAllOrganizations();

        assertTrue(orgs.contains(org));
        assertTrue(orgs.contains(org2));
        assertEquals(orgs.size(), 2);
    }

    /**
     * Test of updateOrganization method, of class OrganizationDaoDB.
     */
    @Test
    public void testUpdateOrganization() {
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
        sup.setDescription("test description");
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

        Organization fromDao = organizationDao.getOrganizationById(org.getId());
        assertEquals(fromDao, org);

        org.setName("test name update");

        organizationDao.updateOrganization(org);

        assertNotEquals(fromDao, org);

        fromDao = organizationDao.getOrganizationById(org.getId());
        assertEquals(org, fromDao);

    }

    /**
     * Test of deleteOrganizationById method, of class OrganizationDaoDB.
     */
    @Test
    public void testDeleteOrganizationById() {
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
        sup.setDescription("test description");
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
        
        Organization fromDao = organizationDao.getOrganizationById(org.getId());
        assertEquals(fromDao, org);
        
        organizationDao.deleteOrganizationById(org.getId());
        
        fromDao = organizationDao.getOrganizationById(org.getId());
        assertNull(fromDao);
    }

}
