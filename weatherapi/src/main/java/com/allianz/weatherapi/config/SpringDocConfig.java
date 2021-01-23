package com.allianz.weatherapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Allianz Weather Api")
                        .version("v0.1")
                        .contact(new Contact()
                                .name("Allianz GmbH")
                                .email("test@test.com")
                                .url("https://www.allianz.com/en.html")));
    }
}
