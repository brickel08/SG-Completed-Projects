/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.models;

import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author benrickel
 */
public class Location {
    
    private int id;
    
    @Size(max = 45, message = "The name of the Location must be 45 characters or less.")
    @Pattern(regexp = "^[a-zA-Z0-9,.\\s]+$", message = "Please enter a valid name for the Location.")
    private String name;
    
    @Size(max = 10, message = "The street number must be 10 characters or less.")
    @Pattern(regexp = "^[0-9]+$", message = "Please enter a valid street number.")
    private String streetNumber;
    
    @Size(max = 45, message = "The street name must be 45 characters or less.")
    @Pattern(regexp = "^[a-zA-Z.\\s]+$", message = "Please enter a valid street name.")
    private String streetName;
    
    @Size(max = 45, message = "The name of the city must be 45 characters or less.")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Please enter a valid city name.")
    private String city;
    
    @Size(max = 2, min = 2, message = "The state abbreviation must be 2 capital letters.")
    @Pattern(regexp = "^[A-Z]+$", message = "Please enter a valid state abbreviation.")
    private String state;
    
    @Size(max = 5, min = 5, message = "The zip code must be 5 digits.")
    @Pattern(regexp = "^[0-9]+$", message = "Please enter a valid zip code. ex: 11111")
    private String zip;
    
    @NotBlank(message = "Please enter a description for the Location.")
    @Size(max = 160, message = "The description of the Location must be 160 characters or less.")
    private String description;
    
    @DecimalMax(value = "90.00", message = "Latitude can only be 90 degrees or less.")
    @DecimalMin(value = "-90.00", message = "Latitude can only be -90 degrees or more.")
    @Digits(integer = 8, fraction = 6, message = "Please enter a valid value for Latitude.")
    @NotNull(message = "Please enter a valid value for latitude.")
    private BigDecimal latitude;
    
    @DecimalMax(value = "180.00", message = "Longitude can only be 180 degrees or less.")
    @DecimalMin(value = "-180.00", message = "Longitude can only be -180 degrees or more.")
    @Digits(integer = 9, fraction = 6, message = "Please enter a valid value for Longitude.")
    @NotNull(message = "Please enter a valid value for longitude.")
    private BigDecimal longitude;
    
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

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
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
        hash = 89 * hash + this.id;
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.streetNumber);
        hash = 89 * hash + Objects.hashCode(this.streetName);
        hash = 89 * hash + Objects.hashCode(this.city);
        hash = 89 * hash + Objects.hashCode(this.state);
        hash = 89 * hash + Objects.hashCode(this.zip);
        hash = 89 * hash + Objects.hashCode(this.description);
        hash = 89 * hash + Objects.hashCode(this.latitude);
        hash = 89 * hash + Objects.hashCode(this.longitude);
        hash = 89 * hash + Objects.hashCode(this.pic);
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
        final Location other = (Location) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.streetNumber, other.streetNumber)) {
            return false;
        }
        if (!Objects.equals(this.streetName, other.streetName)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.zip, other.zip)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.pic, other.pic)) {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Location{" + "id=" + id + ", name=" + name + ", streetNumber=" + streetNumber + ", streetName=" + streetName + ", city=" + city + ", state=" + state + ", zip=" + zip + ", description=" + description + ", latitude=" + latitude + ", longitude=" + longitude + ", pic=" + pic + '}';
    }


}
