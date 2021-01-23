package com.allianz.weatherapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Value
@AllArgsConstructor
public class Weather {

    @NotNull
    @Schema(description = "The description of the weather", example = "scattered clouds")
    String condition;

    @Schema(description = "The actual temperature of the city in celsius", example = "15")
    @NotNull
    Integer temperature;

    @Schema(description = "Speed of the wind in km/h", example = "11")
    @PositiveOrZero
    @NotNull
    @JsonProperty("wind_speed")
    Integer windSpeed;
}
