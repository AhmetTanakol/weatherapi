package com.allianz.weatherapi.dto.openmap;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Wind {
    private BigDecimal speed;
}
