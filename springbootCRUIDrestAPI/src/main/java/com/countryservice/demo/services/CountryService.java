package com.countryservice.demo.services;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.services.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public class CountryService {

    //dependency injection
    @Autowired
    CountryRepository countryrep;

    //1st endpoint
    public List<Country> getAllCountries()
    {
        //returns all the data from the table in the form of objects
        //return type is List because multiple objects is being return. A list of Countries
        List<Country> countries=countryrep.findAll(); //external dependency /calling dao layer
        return countries;
    }

    //2nd endpoint
    public Country getCountrybyID(int id) {
        List<Country> countries = countryrep.findAll(); //external dependency
        Country country = null;

        for (Country con : countries)
        {
            if (con.getId() == id)
                country = con;
            }
            return country;
        }

        public Country getCountrybyName (String countryName)
        {
            List<Country> countries = countryrep.findAll(); //external dependency
            Country country = null;

            for (Country con : countries) {
                if (con.getCountryName().equalsIgnoreCase(countryName))
                    country = con;
            }
            return country;
        }

        public Country addCountry (Country country) //country name and country capital but, no id.
        {
            country.setId(getMaxId()); //method to set id from Country POJO/Bean/Entity Class.
            countryrep.save(country); //External dependency from Repository Interface. Data will be saved in Table
            return country; //returns country id, country name and country capital
        }

        //gets data, modify and save in the Table
        public Country updateCountry (Country country)
        {
            countryrep.save(country); //save() method comes from JPA. Also findAll(), findById() from JPA
            return country;
        }

        public void deleteCountry (Country country)
        {
            countryrep.delete(country);
        }

        //Utility method to get maximum id
        public int getMaxId ()
        {
            return countryrep.findAll().size() + 1;
        }
}








