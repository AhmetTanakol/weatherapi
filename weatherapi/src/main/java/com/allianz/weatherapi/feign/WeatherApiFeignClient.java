package com.allianz.weatherapi.feign;

import com.allianz.weatherapi.config.FeignClientConfig;
import com.allianz.weatherapi.dto.openmap.OpenMapWeatherDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        value = "weather-api",
        url = "${open-weather-api.url}",
        configuration = FeignClientConfig.class
)
public interface WeatherApiFeignClient {

    @GetMapping
    OpenMapWeatherDto getWeather(@RequestParam(name = "q") String cityName);
}
