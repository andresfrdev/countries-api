package com.sdg.countries.services.impl;

import com.sdg.countries.clients.CountryApiClient;
import com.sdg.countries.components.CountryComponent;
import com.sdg.countries.dtos.CountryDto;
import com.sdg.countries.jpa.entities.Country;
import com.sdg.countries.mappers.CountryMapper;
import com.sdg.countries.services.CountryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryComponent component;

    private final CountryMapper mapper;

    private final CountryApiClient apiClient;

    @Override
    @Transactional(readOnly = true)
    public List<CountryDto> getCountries() {
        List<Country> countries = component.getCountries();
        return countries.stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional
    public CountryDto createOrUpdateCountry(String name) {
        return apiClient.getCountryData(name)
            .map(apiDto -> {

                Optional<Country> optionalCountry = component.getCountryByName(name);
                Country country = optionalCountry
                        .map(found -> {
                            found.setPopulation(apiDto.getPopulation());
                            return found;
                        })
                        .orElseGet(() -> mapper.toEntity(apiDto));

                Country saved = component.saveCountry(country);
                return mapper.toDto(saved);
            })
            .orElseThrow(() -> new EntityNotFoundException("Country not found in external API: " + name));
    }
}
