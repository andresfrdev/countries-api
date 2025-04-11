package com.sdg.countries.services;

import com.sdg.countries.dtos.CountryDto;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public interface CountryService {

    List<CountryDto> getCountries();

    CountryDto createOrUpdateCountry(String name);
}
