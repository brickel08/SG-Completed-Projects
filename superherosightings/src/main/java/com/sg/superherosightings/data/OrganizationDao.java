/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.models.Organization;
import com.sg.superherosightings.models.Super;
import java.util.List;

/**
 *
 * @author benrickel
 */
public interface OrganizationDao {

    Organization getOrganizationById(int id);

    List<Organization> getAllOrganizations();

    Organization addOrganization(Organization org);

    void updateOrganization(Organization org);

    void deleteOrganizationById(int id);
    
}
