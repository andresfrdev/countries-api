package com.sdg.countries.controllers;

import com.sdg.countries.dtos.CountryDto;
import com.sdg.countries.dtos.SaveCountryRequestDto;
import com.sdg.countries.services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CountryController implements CountriesApi {

    private final CountryService service;

    @Override
    public ResponseEntity<List<CountryDto>> getCountryData() {
        return ResponseEntity.ok(service.getCountries());
    }

    @Override
    public ResponseEntity<CountryDto> saveCountry(SaveCountryRequestDto request) {
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        return ResponseEntity.ok(service.createOrUpdateCountry(request.getName()));
    }
}
