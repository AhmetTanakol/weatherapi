package com.allianz.weatherapi.dto.openmap;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OpenMapWeatherDto {
    private List<WeatherDto> weather;
    private Main main;
    private Wind wind;
}
