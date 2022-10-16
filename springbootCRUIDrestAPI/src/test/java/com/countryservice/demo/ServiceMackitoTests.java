package com.countryservice.demo;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.services.repositories.CountryRepository;
import com.countryservice.demo.services.CountryService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ServiceMackitoTests.class})
public class ServiceMackitoTests {

    @Mock
    CountryRepository countryrep; //CountryRepository=Class, countryrep=object
    @InjectMocks
    CountryService countryService;

    public List<Country> mycountries;

    @Test
    @Order(1)
    public void test_getAllCountries()
    {
        List<Country> mycountries=new ArrayList<Country>();
        mycountries.add(new Country(1, "Ghana", "Accra"));
        mycountries.add(new Country(2, "USA", "Washington"));

        when(countryrep.findAll()).thenReturn(mycountries); //mocked external dependency
        assertEquals(2,countryService.getAllCountries().size());
    }

    @Test
    @Order(2)
    public void test_getCountryByID()
    {
        mycountries= new ArrayList<>();
        mycountries.add(new Country(1, "Ghana", "Accra"));
        mycountries.add(new Country(2, "USA", "Washington"));

        int countryID = 1;

        when(countryrep.findAll()).thenReturn(mycountries); //mocked external dependency
        assertEquals(countryID, countryService.getCountrybyID(countryID).getId());//fetch country id with getId() and compare w/countryID
    }

    @Test
    @Order(3)
    public void test_getCountryByName()
    {
        mycountries= new ArrayList<>();
        mycountries.add(new Country(1, "Ghana", "Accra"));
        mycountries.add(new Country(2, "USA", "Washington"));

        String countryName = "Ghana";

        when(countryrep.findAll()).thenReturn(mycountries); //mocked external dependency
        assertEquals(countryName, countryService.getCountrybyName(countryName).getCountryName());
    }

    @Test
    @Order(4)
    public void test_addCountry()
    {

        Country country=new Country(3, "Liberia", "Monrovia");

        when(countryrep.save(country)).thenReturn(country); //mocked external dependency
        assertEquals(country, countryService.addCountry(country));
    }

    @Test
    @Order(5)
    public void test_updateCountry()
    {
        Country country=new Country(3, "Germany", "Berlin");

        when(countryrep.save(country)).thenReturn(country); //mocked external dependency
        assertEquals(country, countryService.updateCountry(country));

    }

    @Test
    @Order(6)
    public void test_deleteCountry()
    {
        Country country=new Country(3, "Germany", "Berlin");
        countryService.deleteCountry(country);
        verify(countryrep,times(1)).delete(country);
    }
}



