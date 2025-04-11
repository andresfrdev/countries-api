package com.sdg.countries.clients;

import com.sdg.countries.clients.dtos.CountryApiResponseDto;
import com.sdg.countries.exceptions.CountryNotFoundException;
import com.sdg.countries.exceptions.ExternalApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CountryApiClient {

    private final WebClient webClient;

    public Optional<CountryApiResponseDto> getCountryData(String name) {
        try {
            log.info("Making request to RestCountries API for country: {}", name);

            // Llamada a la API externa
            List<CountryApiResponseDto> response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v3.1/name/{name}")
                            .queryParam("fields", "name,population")
                            .queryParam("fullText", "true") // fuerza coincidencia exacta del nombre
                            .build(name))
                    .retrieve()

                    .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                        if (clientResponse.statusCode() == HttpStatus.NOT_FOUND) {
                            return Mono.error(new CountryNotFoundException("Country not found: " + name));
                        }
                        return Mono.error(new ExternalApiException("Client error from RestCountries API"));
                    })

                    .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                        return Mono.error(new ExternalApiException("Server error from RestCountries API"));
                    })
                    .bodyToMono(new ParameterizedTypeReference<List<CountryApiResponseDto>>() {})
                    .block();

            if (response != null && !response.isEmpty()) {
                log.info("Received response from RestCountries API, processing countries data.");

                for (CountryApiResponseDto country : response) {
                    if (country.getName().getCommon().equalsIgnoreCase(name)) {
                        log.info("Found matching country: {}", country.getName().getCommon());
                        return Optional.of(country);
                    }
                }
            }

            log.warn("No matching country found in the response: {}", name);
            throw new CountryNotFoundException("Country not found: " + name);

        } catch (CountryNotFoundException | ExternalApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ExternalApiException("Unexpected error calling RestCountries API", e);
        }
    }
}