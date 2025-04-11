package com.sdg.countries.clients.dtos;

import lombok.Data;

@Data
public class CountryApiResponseDto {
    private NameDto name;
    private long population;

    @Data
    public static class NameDto {
        private String common;
    }
}
