package com.allianz.weatherapi.dto.openmap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenMapWeatherDto {
    private List<WeatherDto> weather;
    private Main main;
    private Wind wind;
}
