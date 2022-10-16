package com.countryservice.demo.services.repositories;

import com.countryservice.demo.beans.Country;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CountryRepository extends JpaRepository<Country, Integer>
{
}
