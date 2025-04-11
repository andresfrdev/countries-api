package com.sdg.countries.client;

import com.sdg.countries.clients.CountryApiClient;
import com.sdg.countries.clients.dtos.CountryApiResponseDto;
import com.sdg.countries.exceptions.CountryNotFoundException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CountryApiClientTest {

    private MockWebServer mockWebServer;
    private CountryApiClient apiClient;

    @BeforeEach
    void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        WebClient client = WebClient.builder()
                .baseUrl(mockWebServer.url("/").toString())
                .build();

        apiClient = new CountryApiClient(client);
    }

    @AfterEach
    void teardown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void getCountryData_returnsData() throws Exception {
        String responseBody = """
        [{
            "name": {"common": "Argentina"},
            "population": 45000000
        }]
        """;

        mockWebServer.enqueue(new MockResponse()
                .setBody(responseBody)
                .addHeader("Content-Type", "application/json"));

        Optional<CountryApiResponseDto> result = apiClient.getCountryData("Argentina");

        assertTrue(result.isPresent());
        assertEquals("Argentina", result.get().getName().getCommon());
    }

    @Test
    void getCountryData_notFoundThrowsException() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(404));

        assertThrows(CountryNotFoundException.class,
                () -> apiClient.getCountryData("Wakanda"));
    }
}
