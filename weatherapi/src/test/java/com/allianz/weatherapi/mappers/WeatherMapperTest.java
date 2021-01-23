package com.allianz.weatherapi.mappers;

import com.allianz.weatherapi.dto.Weather;
import com.allianz.weatherapi.dto.openmap.Main;
import com.allianz.weatherapi.dto.openmap.OpenMapWeatherDto;
import com.allianz.weatherapi.dto.openmap.WeatherDto;
import com.allianz.weatherapi.dto.openmap.Wind;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WeatherMapperTest {

    private static final WeatherMapper MAPPER = Mappers.getMapper(WeatherMapper.class);

    @Test
    void map() {
        Main main = new Main(new BigDecimal("4.2"));
        Wind wind = new Wind(new BigDecimal("7.82"));
        List<WeatherDto> descriptions = List.of(new WeatherDto("cloudy"), new WeatherDto("cold"));
        OpenMapWeatherDto dto = new OpenMapWeatherDto(descriptions, main, wind);
        Weather mappedData = MAPPER.map(dto);
        assertNotNull(mappedData);
        assertEquals("cloudy,cold", mappedData.getCondition());
        assertEquals(4, mappedData.getTemperature());
        assertEquals(8, mappedData.getWindSpeed());
    }
}
