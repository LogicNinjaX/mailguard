package com.example.mailguard;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@OpenAPIDefinition(
		servers = @Server(url = "http://localhost:8080", description = "Local dev server")
)
@SpringBootApplication
@EnableMethodSecurity
@EnableAsync
public class MailguardApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailguardApplication.class, args);
	}

}
