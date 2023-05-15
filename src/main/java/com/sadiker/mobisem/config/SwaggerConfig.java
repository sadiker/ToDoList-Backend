package com.sadiker.mobisem.config;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true)
                .apiInfo(new ApiInfoBuilder()
                        .contact(new Contact("SA.DİKER", "https://shopney.co/", "email@email.com"))
                        .title("Mobisem API")
                        .description("Kullanıcı kaydı ve TODO List")
                        .version("v1.0").build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sadiker.mobisem"))
                .paths(PathSelectors.any()).build();
    }

}
