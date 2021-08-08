/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.models;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author benrickel
 */
public class Super {
    
    private int id;
    
    @Pattern(regexp = "^[a-zA-Z0-9,.\\s]+$", message = "Please enter a valid name for the Super Being.")
    @Size(max = 45, message = "Super Being names can only be 45 characters or less.")
    private String name;
    
    private String morality;
    
    @NotBlank(message = "Please provide a short description of the Super Being.")
    @Size(max = 160, message = "Descriptions can only be 160 characters or less.")
    private String description;
    
    private Power power;
    
    private String pic;

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

    public String getMorality() {
        return morality;
    }

    public void setMorality(String morality) {
        this.morality = morality;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Power getPower() {
        return power;
    }

    public void setPower(Power power) {
        this.power = power;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.morality);
        hash = 29 * hash + Objects.hashCode(this.description);
        hash = 29 * hash + Objects.hashCode(this.power);
        hash = 29 * hash + Objects.hashCode(this.pic);
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
        final Super other = (Super) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.morality, other.morality)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.pic, other.pic)) {
            return false;
        }
        if (!Objects.equals(this.power, other.power)) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "Super{" + "id=" + id + ", name=" + name + ", morality=" + morality + ", description=" + description + ", power=" + power + ", pic=" + pic + '}';
    }
    

}
