package com.blog.config;

//import io.swagger.v3.oas.models.OpenAPI;
//import jdk.jfr.internal.jfc.model.Constraint;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;

import java.util.Collections;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//@Configuration
//public class SwaggerConfig {
//
//	@Bean
//	public Docket api() {
//		return new Docket(DocumentationType.SWAGGER_2)
//				.apiInfo(getInfo())
//				.select()
//				.apis(RequestHandlerSelectors.any())
//				.paths(PathSelectors.any())
//				.build();
//	}
//
//	private ApiInfo getInfo() {
//		return new ApiInfo("Blogging Application: Backend Course" , "This project is developed to learn to create real
//		life Apis", "1.0", "Terms of service", null, "License of Apis", "Api license URL", Collections.emptyList());
//	}
//}

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Blogging Application API")
                        .version("1.0")
                        .description("API documentation for the Blogging Application"));
    }
}
/*
Generated API Documentation:

- After starting the application, Swagger generates API documentation and exposes it at a specific URL, typically:
  http://localhost:8080/swagger-ui.html
- Or with SpringDoc (OpenAPI 3): http://localhost:8080/swagger-ui/index.html

Features of Swagger UI:

- Lists all available endpoints grouped by controllers (e.g., AuthController, PostController).
- Displays details like HTTP methods (GET, POST, PUT, DELETE), required request parameters, and response models (DTOs).
- Allows users to test APIs directly by filling in the necessary inputs and clicking "Execute."

 */