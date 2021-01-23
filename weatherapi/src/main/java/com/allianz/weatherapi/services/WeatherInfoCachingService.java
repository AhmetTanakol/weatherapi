package com.allianz.weatherapi.services;

import com.allianz.weatherapi.dto.Weather;
import com.allianz.weatherapi.utils.WeatherUtil;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class WeatherInfoCachingService {

    private static final String CURRENT_WEATHER_KEY_PREFIX = "CURRENT_WEATHER_KEY_%s";
    private final RedisTemplate<String, String> redisTemplateString;

    @Nullable
    Weather getCachedWeatherInfo(@NotNull String city) {
        String key = generateKey(city);
        final String value = redisTemplateString.<String, String>opsForHash().get(key, city);
        return WeatherUtil.deserialize(value);
    }

    //Hourly weather info is acceptable.
    void cacheWeatherInfo(@NotNull String city,
                          @NotNull Weather weather) {
        String key = generateKey(city);
        String infoToCache = WeatherUtil.serialize(weather);
        redisTemplateString.<String, String>opsForHash().put(key, city, infoToCache);
        redisTemplateString.expire(key, 1, TimeUnit.HOURS);
    }

    private String generateKey(@NotNull String city) {
        return String.format(CURRENT_WEATHER_KEY_PREFIX, city);
    }
}
