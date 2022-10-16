package com.countryservice.demo;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.controllers.CountryController;
import com.countryservice.demo.services.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "com.countryservice.demo")
@ContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes = {ControllerMocMvcTest.class})
public class ControllerMocMvcTest
{
    @Autowired
    MockMvc mockMvc;
    @Mock
    CountryService countryService;
    @InjectMocks
    CountryController countryController;

    List<Country> my_countries;
    Country country;

    @BeforeEach
    public void setUp()
    {
        mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
    }

    @Test
    @Order(1)


    public void test_getAllCountries() throws Exception
    {
       my_countries = new ArrayList<>();
       my_countries.add(new Country(1, "Cuba", "Havana"));
       my_countries.add(new Country(2, "Venezuela", "Caracas"));

       when(countryService.getAllCountries()).thenReturn(my_countries);

       this.mockMvc.perform(get("/getcountries"))
               .andExpect(status().isFound())
               .andDo(print());
    }

    @Test
    @Order(2)
    public void test_getCountrybyID() throws Exception {

        country = new Country(2, "Zambia", "Lusaka");
        int countryId = 2;

        when(countryService.getCountrybyID(countryId)).thenReturn(country);
        this.mockMvc.perform(get("/getcountries/{id}", countryId))
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("countryName").value("Zambia"))
                .andExpect(MockMvcResultMatchers.jsonPath("countryCapital").value("Lusaka"))
                .andDo(print());

    }

    @Test
    @Order(3)
    public void test_getCountrybyName() throws Exception {

        country = new Country(5, "Uganda", "Kampala");
        String country_name = "Uganda";

        when(countryService.getCountrybyName(country_name)).thenReturn(country);
        this.mockMvc.perform(get("/getcountries/countryname").param("name", "Uganda")) //parameter name from CountryController class
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("countryName").value("Uganda"))
                .andExpect(MockMvcResultMatchers.jsonPath("countryCapital").value("Kampala"))
                .andDo(print());
    }

    @Test
    @Order(4)
    public void test_addCountry() throws Exception {
        country = new Country(4, "Russia", "Moscow");

        when(countryService.addCountry(country)).thenReturn(country);

        //MockMvc only accepts JSON format, it does not accept Java Objects. JSON Request and JSON Response
        //ObjectMapper converts Java objects into JSON format
        ObjectMapper mapper = new ObjectMapper();
        String jsonbody = mapper.writeValueAsString(country);

        this.mockMvc.perform(post("/addcountry")
                        .content(jsonbody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @Order(5)
    public void test_updateCountry() throws Exception {
        country = new Country(4, "Panama", "Panama City");
        int countryId = 4;

        when(countryService.getCountrybyID(countryId)).thenReturn(country);
        when(countryService.updateCountry(country)).thenReturn(country);

        ObjectMapper mapper=new ObjectMapper();
        String jsonbody = mapper.writeValueAsString(country);

        this.mockMvc.perform(put("/updatecountry/{id}", countryId)
                        .content(jsonbody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("countryName").value("Panama"))
                .andExpect(MockMvcResultMatchers.jsonPath("countryCapital").value("Panama City"))
                .andDo(print());
    }

    @Test
    @Order(6)
    public void test_deleteCountry() throws Exception {
        country = new Country(4, "Panama", "Panama City");
        int countryId = 4;
        when(countryService.getCountrybyID(countryId)).thenReturn(country);

        this.mockMvc.perform(delete("/deletecountry/{id}", countryId))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
