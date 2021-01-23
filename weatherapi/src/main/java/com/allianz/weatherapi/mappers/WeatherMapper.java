package com.allianz.weatherapi.mappers;

import com.allianz.weatherapi.dto.Weather;
import com.allianz.weatherapi.dto.openmap.OpenMapWeatherDto;
import com.allianz.weatherapi.dto.openmap.WeatherDto;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface WeatherMapper {

    @Mapping(target = "condition", source = "weather", qualifiedByName = "mapDescription")
    @Mapping(target = "temperature", source = "main.temp", qualifiedByName = "mapBigDecimalToInt")
    @Mapping(target = "windSpeed", source = "wind.speed", qualifiedByName = "mapBigDecimalToInt")
    Weather map(OpenMapWeatherDto dto);

    @Named("mapDescription")
    default String mapDescription(List<WeatherDto> weather) {
        return weather.stream()
                .map(WeatherDto::getDescription)
                .collect(Collectors.joining(","));
    }

    //Converting BigDecimal to int can cause information loss. In this use case, it is acceptable
    //Since both temperature and wind speed values are in acceptable range.
    @Named("mapBigDecimalToInt")
    default Integer mapBigDecimalToInt(BigDecimal num) {
        return num.setScale(0, RoundingMode.HALF_EVEN).intValue();
    }
}
