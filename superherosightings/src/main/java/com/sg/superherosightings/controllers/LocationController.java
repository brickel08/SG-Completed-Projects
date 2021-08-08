/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controllers;

import com.sg.superherosightings.data.LocationDao;
import com.sg.superherosightings.models.Location;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author benrickel
 */
@Controller
public class LocationController {

    @Autowired
    LocationDao locationDao;

    Set<ConstraintViolation<Location>> violations = new HashSet();

    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locs = locationDao.getAllLocations();
        model.addAttribute("locations", locs);
        model.addAttribute("errors", violations);
        return "locations";
    }

    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest req) {
        String name = req.getParameter("name");
        String streetNum = req.getParameter("streetNumber");
        String streetName = req.getParameter("streetName");
        String city = req.getParameter("city");
        String state = req.getParameter("state");
        String zip = req.getParameter("zip");
        String lat = req.getParameter("latitude");
        String lon = req.getParameter("longitude");
        String desc = req.getParameter("description");
        String pic = req.getParameter("pic");

        Location loc = new Location();
        loc.setName(name);
        loc.setStreetNumber(streetNum);
        loc.setStreetName(streetName);
        loc.setCity(city);
        loc.setState(state);
        loc.setZip(zip);
        loc.setDescription(desc);
        loc.setPic(pic);

        if (!lat.isEmpty()) {
            loc.setLatitude(new BigDecimal(lat));
        }

        if (!lon.isEmpty()) {
            loc.setLongitude(new BigDecimal(lon));
        }

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(loc);

        if (violations.isEmpty()) {
            locationDao.addLocation(loc);
        }

        return "redirect:/locations";
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("id"));
        locationDao.deleteLocationById(id);

        return "redirect:/locations";
    }

    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest req, Model model) {
        int id = Integer.parseInt(req.getParameter("id"));
        Location loc = locationDao.getLocationById(id);

        model.addAttribute("location", loc);
        model.addAttribute("errors", violations);
        return "editLocation";
    }

    @PostMapping("editLocation")
    public String performEditLocation(@Valid Location loc, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "editLocation";
        }

        locationDao.updateLocation(loc);
        return "redirect:/locations";
    }

    @GetMapping("locationDetail")
    public String locationDetails(Integer id, Model model) {
        Location loc = locationDao.getLocationById(id);
        model.addAttribute("location", loc);
        return "locationDetail";
    }

}
