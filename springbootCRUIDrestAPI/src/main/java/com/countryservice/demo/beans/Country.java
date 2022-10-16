package com.countryservice.demo.beans;

import lombok.Data;

import javax.persistence.*;

//bean/POJO/entity class
@Entity
@Table(name = "Country") //Name of table, which should be the same name as the class name. Entity
@Data
public class Country {

    //Attributes
    @Id //Id primary key in the database
    @Column(name = "id") //database column
    @GeneratedValue //generate id automatically
    int id;

    @Column(name = "country_name") //database column
    String countryName;

    @Column(name = "capital") //database column
    String countryCapital;

    public Country(){}

    public Country(int id, String countryName, String countryCapital) {

        this.id = id;
        this.countryName = countryName;
        this.countryCapital = countryCapital;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCapital() {
        return countryCapital;
    }

    public void setCountryCapital(String countryCapital) {
        this.countryCapital = countryCapital;
    }
}
