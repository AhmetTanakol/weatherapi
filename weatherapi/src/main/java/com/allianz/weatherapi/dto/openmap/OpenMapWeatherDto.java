package com.allianz.weatherapi.dto.openmap;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OpenMapWeatherDto {
    private List<WeatherDto> weather = new ArrayList<>();
    private Main main;
    private Wind wind;
}
