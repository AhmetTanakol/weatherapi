package com.allianz.weatherapi.services;

import com.allianz.weatherapi.dto.Weather;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class WeatherInfoCachingServiceTest {

    @Mock
    private RedisTemplate<String, String> redisTemplateString;

    @Mock
    private HashOperations<String, Object, Object> opsForHash;

    private WeatherInfoCachingService weatherInfoCachingService;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        weatherInfoCachingService = new WeatherInfoCachingService(redisTemplateString);

        when(redisTemplateString.expire(anyString(), anyLong(), any(TimeUnit.class))).thenReturn(true);
        when(redisTemplateString.opsForHash()).thenReturn(opsForHash);
        String data = "broken clouds;3;5";
        when(opsForHash.get(eq("CURRENT_WEATHER_KEY_Munich"), eq("Munich"))).then(invocation -> {
            if ("CURRENT_WEATHER_KEY_Munich".equals(invocation.getArgument(0))
                    && "Munich".equals(invocation.getArgument(1))) {
                return data;
            }
            return null;
        });
    }

    @AfterEach
    @SneakyThrows
    void close() {
        autoCloseable.close();
    }

    @Test
    void test_GettingUnknownWeatherInfo_ReturnsNull() {
        Weather weather = weatherInfoCachingService.getCachedWeatherInfo("London");
        assertNull(weather);
    }

    @Test
    void test_GettingCachedWeatherInfo_ReturnsWeatherInfo() {
        Weather weather = weatherInfoCachingService.getCachedWeatherInfo("Munich");
        assertNotNull(weather);
        assertEquals("broken clouds", weather.getCondition());
        assertEquals(3, weather.getTemperature());
        assertEquals(5, weather.getWindSpeed());
    }

    @Test
    void test_CachingWeatherInfo_InfoCached() {
        Weather weather = new Weather("rainy", 10, 12);
        weatherInfoCachingService.cacheWeatherInfo("Paris", weather);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(redisTemplateString.opsForHash(), times(1)).put(
                eq("CURRENT_WEATHER_KEY_Paris"),
                eq("Paris"),
                captor.capture());
        assertEquals("rainy;10;12", captor.getValue());
        verify(redisTemplateString, times(1)).expire(eq("CURRENT_WEATHER_KEY_Paris"), eq(1L), eq(TimeUnit.HOURS));
    }
}
