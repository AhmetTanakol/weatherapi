package com.allianz.weatherapi.rest;

import com.allianz.weatherapi.dto.Weather;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RequestMapping("/external-api")
@Tag(name = "External Weather API")
public interface WeatherApi {

    @GetMapping("/v1/weather/current")
    @Operation(summary = "Get current weather info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the current weather info")
    })
    Weather getWeather(@Valid @NotBlank @RequestParam String cityName);
}

