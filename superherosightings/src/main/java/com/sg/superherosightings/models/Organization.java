/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.models;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author benrickel
 */
public class Organization {
    
    private int id;
    
    @Pattern(regexp = "^[a-zA-Z0-9,.\\s]+$", message = "Please enter a valid name for the Organization.")
    @Size(max = 45, message = "An Organization name must be fewer than 45 characters.")
    private String name;
    
    @NotBlank(message = "Please enter a short description of the Organization.")
    @Size(max = 160, message = "Organization descriptions must be fewer than 160 characters.")
    private String description;
    
    @Size(max = 10, min = 10, message = "Phone numbers must be 10 digits long. ex: 1234567890")
    @Pattern(regexp = "^[0-9]+$", message = "Please enter a valid phone number.")
    private String phone;
    
    @Size(max = 45, message = "Email addresses must be fewer than 45 characters.")
    @Email(message = "Please provide a valid email address.")
    private String email;
    
    private Location location;

    @NotNull(message = "Please select at least one Super Being.")
    @Size(min = 1, message = "Please select at least one Super Being.")
    private List<Super> supers;
    
    @Size(max = 200, message = "Picture URL's can only be 200 characters long.")
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Super> getSupers() {
        return supers;
    }

    public void setSupers(List<Super> supers) {
        this.supers = supers;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.description);
        hash = 97 * hash + Objects.hashCode(this.phone);
        hash = 97 * hash + Objects.hashCode(this.email);
        hash = 97 * hash + Objects.hashCode(this.location);
        hash = 97 * hash + Objects.hashCode(this.supers);
        hash = 97 * hash + Objects.hashCode(this.pic);
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
        final Organization other = (Organization) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.pic, other.pic)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.supers, other.supers)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Organization{" + "id=" + id + ", name=" + name + ", description=" + description + ", phone=" + phone + ", email=" + email + ", location=" + location + ", supers=" + supers + ", pic=" + pic + '}';
    }

    
}
