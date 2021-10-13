package com.walt.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Driver extends NamedEntity {

    @ManyToOne
    City city;

    public Driver(){}

    public Driver(String name, City city){
        super(name);
        this.city = city;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "city=" + city +
                '}' + super.toString();
    }
}
