package com.sdg.countries.component;

import com.sdg.countries.components.impl.CountryComponentImpl;
import com.sdg.countries.jpa.entities.Country;
import com.sdg.countries.jpa.repositories.CountryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryComponentImplTest {

    @Mock private CountryRepository repository;

    @InjectMocks private CountryComponentImpl component;

    @Test
    void getCountries_returnsFromRepository() {
        when(repository.findAllByOrderByNameAsc()).thenReturn(List.of(new Country()));

        List<Country> result = component.getCountries();

        assertEquals(1, result.size());
    }

    @Test
    void saveCountry_savesCorrectly() {
        Country country = new Country();
        when(repository.save(country)).thenReturn(country);

        Country saved = component.saveCountry(country);

        assertNotNull(saved);
    }

    @Test
    void getCountryByName_returnsOptional() {
        Country c = new Country();
        when(repository.findByNameIgnoreCase("Chile")).thenReturn(Optional.of(c));

        Optional<Country> result = component.getCountryByName("Chile");

        assertTrue(result.isPresent());
    }
}
