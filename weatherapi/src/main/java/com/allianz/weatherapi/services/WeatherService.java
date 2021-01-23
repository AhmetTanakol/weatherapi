package com.allianz.weatherapi.services;

import com.allianz.weatherapi.dto.Weather;
import com.allianz.weatherapi.dto.openmap.OpenMapWeatherDto;
import com.allianz.weatherapi.feign.WeatherApiFeignClient;
import com.allianz.weatherapi.mappers.WeatherMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {

    private static final WeatherMapper WEATHER_MAPPER = Mappers.getMapper(WeatherMapper.class);
    private final WeatherApiFeignClient weatherApiFeignClient;
    private final WeatherInfoCachingService weatherInfoCachingService;

    public Weather getCurrentWeatherBy(@NotNull String cityName) {
        if (StringUtils.isBlank(cityName)) {
            throw new IllegalArgumentException("City name cannot be null");
        }
        Weather cachedWeatherInfo = weatherInfoCachingService.getCachedWeatherInfo(cityName);
        if (cachedWeatherInfo != null) {
            return cachedWeatherInfo;
        }

        return fetchWeatherInfo(cityName);
    }

    private Weather fetchWeatherInfo(@NotNull String cityName) {
        try {
            OpenMapWeatherDto responseDto = weatherApiFeignClient.getWeather(cityName);
            Weather weather = WEATHER_MAPPER.map(responseDto);
            weatherInfoCachingService.cacheWeatherInfo(cityName, weather);
            return weather;
        } catch (FeignException e) {
            String message = "Cannot retrieve weather info, please try again later";
            log.info(message, e);
            throw new IllegalStateException(message);
        }
    }
}
