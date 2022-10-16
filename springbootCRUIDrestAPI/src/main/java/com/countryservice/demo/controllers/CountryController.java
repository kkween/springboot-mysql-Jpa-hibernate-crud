package com.countryservice.demo.controllers;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping
public class CountryController {

    @Autowired //Dependency Injection, Used to create a variable to access all the methods the Service class.
    CountryService countryService;

    @GetMapping("/getcountries")
    public ResponseEntity<List<Country>> getCountries() {
        try {
            List<Country> countries = countryService.getAllCountries();
            return new ResponseEntity<List<Country>>(countries, HttpStatus.FOUND);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Path Variable/Parameter
    //Response type is ResponseEntity<Country> instead of Country
    @GetMapping("/getcountries/{id}")
    public ResponseEntity<Country> getCountrybyID(@PathVariable(value="id") int id)//returns a single country based on id passed
    {
        try
        {
            //If id is not available,then this statement will throw exception HttpStatus.NOT_FOUND
            //If id is available then return 'country' object along with ResponseEntity of HttpStatus FOUND.
            Country country = countryService.getCountrybyID(id); //id passed
            return new ResponseEntity<Country>(country,HttpStatus.FOUND);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    //Query Parameter
    //Response type is ResponseEntity<Country> instead of Country
    //returns a single country based on http request name(countryName) passed
    @GetMapping("/getcountries/countryname")
    public ResponseEntity<Country> getCountrybyName(@RequestParam(value="name") String countryName)
    {
        try
        {
            //If name is not available,then this statement will throw exception HttpStatus.NOT_FOUND
            //If id is available then returns 'country' object along with ResponseEntity of HttpStatus FOUND.
            Country country = countryService.getCountrybyName(countryName); //name passed from http request
            return new ResponseEntity<Country>(country,HttpStatus.FOUND); //country variable from above line
        }
        catch (NoSuchElementException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addcountry")
    public ResponseEntity<Country> addCountry(@RequestBody Country country) //adds new country in database
    {
        try
        {
            //country name and country capital but, no id, will be generated automatically
            country = countryService.addCountry(country);
            return new ResponseEntity<Country>(country, HttpStatus.CREATED);
        }
        catch (NoSuchElementException e)
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/updatecountry/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable(value = "id") int id, @RequestBody Country country)
    {
        try
        {
            Country existCountry = countryService.getCountrybyID(id); //returns country object based on id if available
            existCountry.setCountryName(country.getCountryName()); //setCountryName() from POJO class(Country.java) of that record, then gets it.
            existCountry.setCountryCapital(country.getCountryCapital()); //setCountryCapital() from POJO class(Country.java), then gets it.

            Country updated_country = countryService.updateCountry(existCountry);
            return new ResponseEntity<Country>(updated_country,HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

    }

    @DeleteMapping("/deletecountry/{id}")
    public ResponseEntity<Country> deleteCountry(@PathVariable(value = "id") int id) {
        Country country = null;
        try
        {
            country=countryService.getCountrybyID(id);
            countryService.deleteCountry(country);
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        return new ResponseEntity<Country>(country, HttpStatus.OK);
    }
}
