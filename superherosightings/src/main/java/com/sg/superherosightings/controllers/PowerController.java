/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controllers;

import com.sg.superherosightings.data.PowerDao;
import com.sg.superherosightings.models.Power;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
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
public class PowerController {

    @Autowired
    PowerDao powerDao;

    Set<ConstraintViolation<Power>> violations = new HashSet();

    @GetMapping("powers")
    public String displayPowers(Model model) {
        List<Power> powers = powerDao.getAllPowers();
        model.addAttribute("powers", powers);
        model.addAttribute("errors", violations);
        return "powers";
    }

    @PostMapping("addPower")
    public String addPower(HttpServletRequest request) {
        String name = request.getParameter("name");
        String element = request.getParameter("element");
        String desc = request.getParameter("description");

        Power power = new Power();
        power.setName(name);
        power.setElement(element);
        power.setDescription(desc);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(power);

        if (violations.isEmpty()) {
            powerDao.addPower(power);
        }

        return "redirect:/powers";
    }

    @GetMapping("deletePower")
    public String deletePower(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("id"));
        powerDao.deletePowerById(id);

        return "redirect:/powers";
    }

    @GetMapping("editPower")
    public String editPower(HttpServletRequest req, Model model) {
        int id = Integer.parseInt(req.getParameter("id"));
        Power power = powerDao.getPowerById(id);

        model.addAttribute("power", power);
        return "editPower";
    }

    @PostMapping("editPower")
    public String performEditPower(@Valid Power power, BindingResult result) {
        if(result.hasErrors()) {
            return "editPower";
        }
        powerDao.updatePower(power);
        return "redirect:/powers";
    }

}
