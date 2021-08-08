/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controllers;

import com.sg.superherosightings.data.LocationDao;
import com.sg.superherosightings.data.SightingDao;
import com.sg.superherosightings.data.SuperDao;
import com.sg.superherosightings.models.Location;
import com.sg.superherosightings.models.Sighting;
import com.sg.superherosightings.models.Super;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
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
public class SightingController {

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    SuperDao superDao;

    Set<ConstraintViolation<Sighting>> violations = new HashSet();

    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        List<Super> supers = superDao.getAllSupers();
        List<Location> locs = locationDao.getAllLocations();
        model.addAttribute("sightings", sightings);
        model.addAttribute("supers", supers);
        model.addAttribute("locations", locs);
        model.addAttribute("errors", violations);

        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest req) {
        Sighting sighting = new Sighting();
        String superId = req.getParameter("superId");
        String locationId = req.getParameter("locationId");
        String date = req.getParameter("sightingDate");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate userDate = LocalDate.parse(date, formatter);

        sighting.setSupe(superDao.getSuperById(Integer.parseInt(superId)));
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
        sighting.setDate(userDate);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);

        if (violations.isEmpty()) {
            sightingDao.addSighting(sighting);
        }

        return "redirect:/sightings";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        sightingDao.deleteSightingById(id);
        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {

        Sighting sighting = sightingDao.getSightingById(id);
        List<Super> supers = superDao.getAllSupers();
        List<Location> locs = locationDao.getAllLocations();
        model.addAttribute("sighting", sighting);
        model.addAttribute("supers", supers);
        model.addAttribute("locations", locs);
        model.addAttribute("errors", violations);
        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(Sighting sighting, HttpServletRequest req, Model model) {
        String superId = req.getParameter("superId");
        String locationId = req.getParameter("locationId");
        String date = req.getParameter("sightingDate");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate userDate = LocalDate.parse(date, formatter);

        sighting.setSupe(superDao.getSuperById(Integer.parseInt(superId)));
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
        sighting.setDate(userDate);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);

        if (violations.isEmpty()) {
            sightingDao.updateSighting(sighting);
            return "redirect:/sightings";
        } else {
            model.addAttribute("supers", superDao.getAllSupers());
            model.addAttribute("locations", locationDao.getAllLocations());
            model.addAttribute("sighting", sighting);
            model.addAttribute("errors", violations);
            return "editSighting";
        }

    }

}
