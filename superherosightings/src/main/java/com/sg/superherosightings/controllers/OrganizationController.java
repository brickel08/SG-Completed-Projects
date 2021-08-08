/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controllers;

import com.sg.superherosightings.data.LocationDao;
import com.sg.superherosightings.data.OrganizationDao;
import com.sg.superherosightings.data.PowerDao;
import com.sg.superherosightings.data.SightingDao;
import com.sg.superherosightings.data.SuperDao;
import com.sg.superherosightings.models.Location;
import com.sg.superherosightings.models.Organization;
import com.sg.superherosightings.models.Super;
import java.util.ArrayList;
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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author benrickel
 */
@Controller
public class OrganizationController {

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

    Set<ConstraintViolation<Organization>> violations = new HashSet();

    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        List<Organization> orgs = organizationDao.getAllOrganizations();
        List<Location> locs = locationDao.getAllLocations();
        List<Super> supers = superDao.getAllSupers();
        model.addAttribute("organizations", orgs);
        model.addAttribute("locations", locs);
        model.addAttribute("supers", supers);
        model.addAttribute("errors", violations);
        return "organizations";
    }

    @PostMapping("addOrganization")
    public String addOrganization(HttpServletRequest req) {
        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String locationId = req.getParameter("locationId");
        String[] superIds = req.getParameterValues("superId");
        String desc = req.getParameter("description");
        String pic = req.getParameter("pic");

        Organization org = new Organization();
        org.setName(name);
        org.setPhone(phone);
        org.setEmail(email);
        org.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
        org.setDescription(desc);
        org.setPic(pic);

        List<Super> supers = new ArrayList();

        if (superIds != null) {

            for (String superId : superIds) {
                supers.add(superDao.getSuperById(Integer.parseInt(superId)));
            }
        }

        org.setSupers(supers);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(org);

        if (violations.isEmpty()) {
            organizationDao.addOrganization(org);
        }

        return "redirect:/organizations";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer id) {
        organizationDao.deleteOrganizationById(id);
        return "redirect:/organizations";
    }

    @GetMapping("editOrganization")
    public String editOrganiztion(Integer id, Model model) {
        Organization org = organizationDao.getOrganizationById(id);
        List<Super> supers = superDao.getAllSupers();
        List<Location> locs = locationDao.getAllLocations();
        model.addAttribute("organization", org);
        model.addAttribute("supers", supers);
        model.addAttribute("locations", locs);
        model.addAttribute("errors", violations);
        return "editOrganization";
    }

//    @PostMapping("editOrganization")
//    public String performEditOrganization(Organization org, BindingResult result, Model model) {
//
//        List<Super> supers = org.getSupers();
//
//        if (result.hasErrors()) {
//            supers = superDao.getAllSupers();
//            List<Location> locs = locationDao.getAllLocations();
//            model.addAttribute("errors", result.getAllErrors());
//            model.addAttribute("organization", org);
//            model.addAttribute("supers", supers);
//            model.addAttribute("locations", locs);
//            return "editOrganization";
//        }
//
//        organizationDao.updateOrganization(org);
//        return "redirect:/organizations";
//    }
    // USE THIS ONE IF ALL ELSE FAILS
    @PostMapping("editOrganization")
    public String performEditOrganization(Organization org, HttpServletRequest req, BindingResult result, Model model) {
        String locationId = req.getParameter("locationId");
        String[] superIds = req.getParameterValues("superId");

        org.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));

        List<Super> supers = new ArrayList();
        if (superIds != null) {
            for (String superId : superIds) {
                supers.add(superDao.getSuperById(Integer.parseInt(superId)));
            }

        } else {
            FieldError error = new FieldError("organization", "supers", "Please select at least one Super Being#");
            result.addError(error);
        }
        org.setSupers(supers);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(org);

        if (violations.isEmpty()) {
            organizationDao.updateOrganization(org);
        } else {
            model.addAttribute("organization", org);
            model.addAttribute("supers", superDao.getAllSupers());
            model.addAttribute("locations", locationDao.getAllLocations());
            return "editOrganization";
        }

        return "redirect:/organizations";

    }

    @GetMapping("organizationDetail")
    public String organizationDetails(Integer id, Model model) {
        Organization org = organizationDao.getOrganizationById(id);
        List<Super> supers = superDao.getAllSupers();
        model.addAttribute("organization", org);
        model.addAttribute("supers", supers);
        return "organizationDetail";
    }

}

/*


Old perform edit method:

    @PostMapping("editOrganization")
    public String performEditOrganization(@Valid Organization org, BindingResult result) {
        if(result.hasErrors()) {
            return "editOrganization";
        }
        organizationDao.updateOrganization(org);
        return "redirect:/organizations";
    }




ORIGINAL:

    @PostMapping("editOrganization")
    public String performEditOrganization(@Valid Organization org, BindingResult result, HttpServletRequest req, Model model) {
        String locationId = req.getParameter("locationId");
        String[] superIds = req.getParameterValues("superId");

        org.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));

        List<Super> supers = new ArrayList();
        if (superIds != null) {
            for (String superId : superIds) {
                supers.add(superDao.getSuperById(Integer.parseInt(superId)));
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("supers", superDao.getAllSupers());
            model.addAttribute("locations", locationDao.getAllLocations());
            model.addAttribute("organization", org);
            model.addAttribute("errors", violations);
            return "editOrganization";
        }

        org.setSupers(supers);
        organizationDao.updateOrganization(org);
        return "redirect:/organizations";
    }

**** THIS ONE WITH RANDY:

    @PostMapping("editOrganization")
    public String performEditOrganization(Organization org, HttpServletRequest req, Model model) {
        String locationId = req.getParameter("locationId");
        String[] superIds = req.getParameterValues("superId");

        org.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));

        List<Super> supers = new ArrayList();
        if (superIds != null) {
            for (String superId : superIds) {
                supers.add(superDao.getSuperById(Integer.parseInt(superId)));
            }
            org.setSupers(supers);
        } //else {
//            FieldError error = new FieldError("organization", "supers", "Please select at least one Super Being#");
//            result.addError(error);
//        }

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(org);

        if (violations.isEmpty()) {
            organizationDao.updateOrganization(org);
        } else {
            model.addAttribute("organization", org);
            model.addAttribute("supers", superDao.getAllSupers());
            model.addAttribute("locations", locationDao.getAllLocations());
            return "editOrganization";
        }

        

        return "redirect:/organizations";

    }

    //THIS ONE throws errors correctly, except doesn't allow for selecting a Super. it does not like the thymeleaf line:
    // th:selected="${organization.supers.contains(super)}"
//    @PostMapping("editOrganization")
//    public String performEditOrganization(@Valid Organization org, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            List<Super> supers = superDao.getAllSupers();
//            List<Location> locs = locationDao.getAllLocations();
//            model.addAttribute("errors", result.getAllErrors());
//            model.addAttribute("organization", org);
//            model.addAttribute("supers", supers);
//            model.addAttribute("locations", locs);
//            return "editOrganization";
//        }
//
//        organizationDao.updateOrganization(org);
//        return "redirect:/organizations";
//    }

    //THIS ONE WITH QUINTEN
//    @PostMapping("editOrganization")
//    public String performEditOrganization(@Valid Organization org, BindingResult result, HttpServletRequest req, Model model) {
//        String locationId = req.getParameter("locationId");
//        String[] superIds = req.getParameterValues("superId");
//
//        org.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
//        
//        result.getRawFieldValue("supers");
//
//        List<Super> supers = new ArrayList();
//        if (superIds != null) {
//            for (String superId : superIds) {
//                supers.add(superDao.getSuperById(Integer.parseInt(superId)));
//            }
//            org.setSupers(supers);
//        }
//        
//        result.getRawFieldValue("supers");
//        
//        List<ObjectError> errors = result.getAllErrors();
//        
//        for(ObjectError error : errors) {
//            if(error.getCode().equals("org.supers") && supers == null) {
//                //do something
//            } else if (errors.size() > 1){
//                
//            }
//            
//        }
//
////        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
////        violations = validate.validate(org);
//
//        if (!result.hasErrors()) {
//            organizationDao.updateOrganization(org);
//        } else {
//            model.addAttribute("organization", org);
//            model.addAttribute("supers", superDao.getAllSupers());
//            model.addAttribute("locations", locationDao.getAllLocations());
//            model.addAttribute("errors", violations);
//            return "editOrganization";
//        }
//
//       
//        return "redirect:/organizations";
//
//    }

//    @PostMapping("editOrganization")
//    public String performEditOrganization(Organization org, HttpServletRequest req) {
//        String locationId = req.getParameter("locationId");
//        String[] superIds = req.getParameterValues("superId");
//
//        org.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
//
//        List<Super> supers = new ArrayList();
//        if (superIds != null) {
//            for (String superId : superIds) {
//                supers.add(superDao.getSuperById(Integer.parseInt(superId)));
//            }
//        }
//
//        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
//        violations = validate.validate(org);
//
//        if (violations.isEmpty()) {
//            organizationDao.updateOrganization(org);
//            return "redirect:/organizations";
//        }
//
//        return "editOrganization";
//
//    }


 */
