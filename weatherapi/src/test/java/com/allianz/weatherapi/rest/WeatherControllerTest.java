package com.allianz.weatherapi.rest;

import com.allianz.weatherapi.services.WeatherService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WeatherControllerTest {

    @Mock
    private WeatherService weatherService;

    private MockMvc mockMvc;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        final WeatherController controller = new WeatherController(weatherService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @AfterEach
    @SneakyThrows
    void close() {
        autoCloseable.close();
    }

    @Test
    @SneakyThrows
    void test_GettingWeatherInfoWithValidCityName_InvokeWeatherService() {
        this.mockMvc.perform(get("/external-api/v1/weather/current?cityName=London"))
                .andExpect(status().isOk());

        verify(weatherService, times(1)).getCurrentWeatherBy(eq("London"));
    }

    @Test
    @SneakyThrows
    void test_GettingWeatherInfoWithoutQueryParamCityName_ThrowException() {
        this.mockMvc.perform(get("/external-api/v1/weather/current"))
                .andExpect(status().is4xxClientError());

        verify(weatherService, times(0));
    }

}
