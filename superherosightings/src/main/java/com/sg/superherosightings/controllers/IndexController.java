/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controllers;

import com.sg.superherosightings.data.SightingDao;
import com.sg.superherosightings.models.Sighting;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author benrickel
 */
@Controller
public class IndexController {

    @Autowired
    SightingDao sightingDao;

    @GetMapping("/")
    public String displayIndex(Model model) {
        List<Sighting> sightings = sightingDao.getAllOrderedSightings();
        model.addAttribute("sightings", sightings);
        return "index";
    }

}
