package com.sdg.countries.components;

import com.sdg.countries.jpa.entities.Country;

import java.util.List;
import java.util.Optional;

public interface CountryComponent {

    List<Country> getCountries();

    Country saveCountry(Country country);

    Optional<Country> getCountryByName(String name);
}
