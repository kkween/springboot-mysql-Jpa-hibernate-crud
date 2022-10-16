package com.countryservice.demo;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.controllers.CountryController;
import com.countryservice.demo.services.CountryService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

//Performs Unit and Integration testing API and WebServices

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = ControllerMackitoTest.class )
public class ControllerMackitoTest {

    public List<Country> mycountries;
    public Country country;

    @Mock
    public CountryService countryService;
    @InjectMocks
    public CountryController countryController ;


    @Test
    @Order(1)
    public void test_getAllCountries() {

        mycountries = new ArrayList<>();

        mycountries.add(new Country(1, "Cameroon", "Yaounde"));
        mycountries.add(new Country(2, "Columba", "Bugota"));

        when(countryService.getAllCountries()).thenReturn((mycountries)); //mocking
        ResponseEntity<List<Country>> response = countryController.getCountries(); //response with status code and body

        assertEquals(HttpStatus.FOUND,response.getStatusCode()); //status code
        assertEquals(2,response.getBody().size()); //response has 2 records therefore, expects 2 records
    }

    @Test
    @Order(2)
    public void test_getCountryByID()
    {
       country = new Country(2, "USA", "Washington");

       int countryId = 2;

        when(countryService.getCountrybyID(2)).thenReturn(country);
        ResponseEntity<Country> response = countryController.getCountrybyID(countryId);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(countryId, response.getBody().getId());
    }

    @Test
    @Order(3)
    public void test_getCountrybyName()
    {
        country = new Country(3, "Ethiopia", "Addis Ababa");
        String my_country = "Ethiopia";

        when(countryService.getCountrybyName(my_country)).thenReturn(country);
        ResponseEntity<Country> response = countryController.getCountrybyName(my_country); //will return id, name, capital, status code

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(my_country, response.getBody().getCountryName());
    }

    @Test
    @Order(4)
    public void test_addCountry()
    {
        country = new Country(4, "Eritrea", "Asmara");

        String countryName = "Eritrea";

        when(countryService.addCountry(country)).thenReturn(country);
        ResponseEntity<Country> response = countryController.addCountry(country);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(countryName, response.getBody().getCountryName());
    }

    @Test
    @Order(5)
    public void test_updateCountry()
    {
        country = new Country(4, "Belize", "Belmopan");
        int countryId = 4;

        when(countryService.getCountrybyID(countryId)).thenReturn(country);
        when(countryService.updateCountry(country)).thenReturn(country);
        ResponseEntity<Country> response = countryController.updateCountry(countryId, country);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(countryId, response.getBody().getId());
        assertEquals("Belize", response.getBody().getCountryName());
        assertEquals("Belmopan", response.getBody().getCountryCapital());
    }

    @Test
    @Order(6)
    public void test_deleteCountry()
    {
        country = new Country(4, "Belize", "Belmopan");
        int countryId = 4;

        when(countryService.getCountrybyID(countryId)).thenReturn(country);
        ResponseEntity<Country> response = countryController.deleteCountry(countryId);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(countryId, response.getBody().getId());
        assertEquals("Belize", response.getBody().getCountryName());
        assertEquals("Belmopan", response.getBody().getCountryCapital());
    }
}
