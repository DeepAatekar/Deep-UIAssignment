package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
				title = "Reward Calculation Assigment",
				description = "API documentation for managing customer, transactions, and reward points.",
				version = "v1",
				contact = @Contact(
							name = "Deep Aatekar",
							email = "deepsureshbhai.a@infosys.com"
							
						)
				
				
				
				),
		security = @SecurityRequirement(name = "basicAuth"),
		
		servers = {
				@Server(
						url = "http://localhost:8080",
						description = "Local Server"
						)
				
		}
		)
@SecuritySchemes({
	@SecurityScheme(name = "basicAuth",
					type = SecuritySchemeType.HTTP,
					scheme = "basic"
			)
})

@SpringBootApplication
public class AssigmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssigmentApplication.class, args);
	}

}
