/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controllers;

import com.sg.superherosightings.data.PowerDao;
import com.sg.superherosightings.data.SuperDao;
import com.sg.superherosightings.models.Power;
import com.sg.superherosightings.models.Super;
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
public class SuperController {

    @Autowired
    PowerDao powerDao;

    @Autowired
    SuperDao superDao;

    Set<ConstraintViolation<Super>> violations = new HashSet();

    @GetMapping("supers")
    public String displaySupers(Model model) {
        List<Super> supers = superDao.getAllSupers();
        List<Power> powers = powerDao.getAllPowers();
        model.addAttribute("supers", supers);
        model.addAttribute("powers", powers);
        model.addAttribute("errors", violations);
        return "supers";
    }

    @PostMapping("addSuper")
    public String addSuper(Super sup, HttpServletRequest req) {
        String powerId = req.getParameter("powerId");

        sup.setPower(powerDao.getPowerById(Integer.parseInt(powerId)));

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sup);

        if (violations.isEmpty()) {
            superDao.addSuper(sup);
        }

        return "redirect:/supers";

    }

    @GetMapping("deleteSuper")
    public String deleteSuper(Integer id) {
        superDao.deleteSuperById(id);
        return "redirect:/supers";
    }

    @GetMapping("editSuper")
    public String editSuper(Integer id, Model model) {
        Super sup = superDao.getSuperById(id);
        List<Power> powers = powerDao.getAllPowers();
        model.addAttribute("super", sup);
        model.addAttribute("powers", powers);
        return "editSuper";
    }


    @PostMapping("editSuper")
    public String performEditSuper(@Valid Super sup, BindingResult result, HttpServletRequest req, Model model) {
        String powerId = req.getParameter("powerId");
        
        sup.setPower(powerDao.getPowerById(Integer.parseInt(powerId)));
        
        if (result.hasErrors()) {
            model.addAttribute("powers", powerDao.getAllPowers());
            model.addAttribute("super", sup);
            return "editSuper";
        }
        superDao.updateSuper(sup);
        return "redirect:/supers";
    }

    @GetMapping("superDetail")
    public String superDetails(Integer id, Model model) {
        Super sup = superDao.getSuperById(id);
        model.addAttribute("super", sup);
        return "superDetail";
    }

}
