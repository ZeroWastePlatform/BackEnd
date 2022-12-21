package com.greenUs.server.global.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {

		Info info = new Info()
			.version("v3.0.0")
			.title("ZeroWaste")
			.description("ZeroWaste Project API");

		return new OpenAPI()
			.info(info);
	}
}