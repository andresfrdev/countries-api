package com.sdg.countries.components.impl;

import com.sdg.countries.components.CountryComponent;
import com.sdg.countries.jpa.entities.Country;
import com.sdg.countries.jpa.repositories.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CountryComponentImpl implements CountryComponent {

    private final CountryRepository repository;

    @Override
    public List<Country> getCountries() {
        return repository.findAllByOrderByNameAsc();
    }

    @Override
    public Country saveCountry(Country country) {
        return repository.save(country);
    }

    @Override
    public Optional<Country> getCountryByName(String name) {
        return repository.findByNameIgnoreCase(name);
    }
}
