package com.turing.turingproject.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket productApi() {
			return new Docket(DocumentationType.SWAGGER_2).forCodeGeneration(Boolean.TRUE).select()
					.apis(RequestHandlerSelectors.basePackage("com.turing.turingproject.controller"))
					.paths(PathSelectors.any())
					.paths(Predicates.not(PathSelectors.regex("/logout.*"))).build()
					.tags(new Tag("Online Store", "All apis relating to online store"));
	}
	
	@Bean
	  UiConfiguration uiConfig() {
		return UiConfigurationBuilder.builder()
		        .docExpansion(DocExpansion.LIST)
		        .defaultModelsExpandDepth(-1)
		        .build();
	  }
}
