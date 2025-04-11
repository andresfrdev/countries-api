package com.sdg.countries.service;

import com.sdg.countries.clients.CountryApiClient;
import com.sdg.countries.clients.dtos.CountryApiResponseDto;
import com.sdg.countries.components.CountryComponent;
import com.sdg.countries.dtos.CountryDto;
import com.sdg.countries.jpa.entities.Country;
import com.sdg.countries.mappers.CountryMapper;
import com.sdg.countries.services.impl.CountryServiceImpl;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CountryServiceImplTest {

    @Mock private CountryComponent component;
    @Mock private CountryApiClient apiClient;
    @Mock private CountryMapper mapper;

    @InjectMocks
    private CountryServiceImpl service;

    @Test
    void getCountries_returnsListOfCountryDto() {
        List<Country> countries = List.of(new Country(1L, "Argentina", 40000000L, null, null));
        when(component.getCountries()).thenReturn(countries);

        CountryDto dto = new CountryDto();
        dto.setName("Argentina");
        dto.setPopulation(40000000L);
        when(mapper.toDto(any())).thenReturn(dto);

        List<CountryDto> result = service.getCountries();

        assertEquals(1, result.size());
        assertEquals("Argentina", result.get(0).getName());
    }

    @Test
    void createOrUpdateCountry_createsNewCountry() {
        String name = "Argentina";
        CountryApiResponseDto apiDto = mock(CountryApiResponseDto.class);
        when(apiClient.getCountryData(name)).thenReturn(Optional.of(apiDto));
        when(component.getCountryByName(name)).thenReturn(Optional.empty());

        Country entity = new Country();
        when(mapper.toEntity(apiDto)).thenReturn(entity);
        when(component.saveCountry(entity)).thenReturn(entity);

        CountryDto dto = new CountryDto();
        when(mapper.toDto(entity)).thenReturn(dto);

        CountryDto result = service.createOrUpdateCountry(name);

        assertNotNull(result);
        verify(component).saveCountry(entity);
    }

    @Test
    void createOrUpdateCountry_throwsExceptionIfNotFound() {
        String name = "Wakanda";
        when(apiClient.getCountryData(name)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.createOrUpdateCountry(name));
    }
}