package com.calling.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "calling-service",
		description = "Client service calling the other service.",
		contact = @Contact(name="Afnan"),
		license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0"),
		version="V1.0"
		))
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
