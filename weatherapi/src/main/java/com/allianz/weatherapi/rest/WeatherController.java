package com.allianz.weatherapi.rest;

import com.allianz.weatherapi.dto.Weather;
import com.allianz.weatherapi.services.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequiredArgsConstructor
public class WeatherController implements WeatherApi {

    private final WeatherService weatherService;

    @Override
    public Weather getWeather(@Valid @NotBlank String cityName) {
        return weatherService.getCurrentWeatherBy(cityName);
    }
}
