package com.sdg.countries.mappers;

import com.sdg.countries.clients.dtos.CountryApiResponseDto;
import com.sdg.countries.dtos.CountryDto;
import com.sdg.countries.jpa.entities.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CountryMapper {

    CountryDto toDto(Country entity);

    Country toEntity(CountryDto dto);

    @Mapping(target = "name", source = "name.common")
    Country toEntity(CountryApiResponseDto dto);
}
