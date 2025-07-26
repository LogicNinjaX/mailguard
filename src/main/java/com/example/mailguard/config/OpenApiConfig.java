package com.example.mailguard.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Mailguard Api")
                                .version("1.0")
                                .description("Documentations for mailguard api")
                                .contact(
                                        new Contact()
                                                .name("Nitish Kr Sahni")
                                                .email("mail.logicninjax@gmail.com")
                                                .url("https://github.com/LogicNinjaX")
                                )
                );

    }
}
