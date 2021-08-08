/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.models;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author benrickel
 */
public class Power {
    
    private int id;
    
    @Pattern(regexp = "^[a-zA-Z0-9,.\\s]+$", message = "Please enter a valid name for the Super Power.")
    @Size(max = 45, message = "A Super Power can only be 45 characters long.")
    private String name;
    
    @Pattern(regexp = "^[a-zA-Z0-9,.\\s]+$", message = "Please enter a valid element for the Super Power.")
    @Size(max = 45, message = "A Super Power element name can only be 45 characters long.")
    private String element;
    
    @NotBlank(message = "Please enter a short description of the Super Power.")
    @Size(max = 45, message = "A description of a Super Power can only 45 characters long.")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + this.id;
        hash = 19 * hash + Objects.hashCode(this.name);
        hash = 19 * hash + Objects.hashCode(this.element);
        hash = 19 * hash + Objects.hashCode(this.description);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Power other = (Power) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.element, other.element)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Power{" + "id=" + id + ", name=" + name + ", element=" + element + ", description=" + description + '}';
    }
    
    
    
}
