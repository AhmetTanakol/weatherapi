package com.allianz.weatherapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
        scanBasePackages = {
                "com.allianz"
        },
        exclude = {
                SecurityAutoConfiguration.class
        }
)
@EnableFeignClients(basePackages = {
        "com.allianz.weatherapi.feign"
})
public class AllianzWeatherApp {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AllianzWeatherApp.class);
        application.run(args);
    }

}
