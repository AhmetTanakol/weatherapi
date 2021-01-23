package com.allianz.weatherapi.utils;

import com.allianz.weatherapi.dto.Weather;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WeatherUtil {

    private final static String WEATHER_INFO_DELIMITER = ";";

    @NotNull
    public static String serialize(Weather weather) {
        return String.format("%s;%d;%d", weather.getCondition(), weather.getTemperature(), weather.getWindSpeed());
    }

    @Nullable
    public static Weather deserialize(String value) {
        if (StringUtils.isNotBlank(value)) {
            String[] weatherInfo = value.split(WEATHER_INFO_DELIMITER);
            return new Weather(weatherInfo[0], Integer.parseInt(weatherInfo[1]), Integer.parseInt(weatherInfo[2]));
        }
        return null;
    }
}
