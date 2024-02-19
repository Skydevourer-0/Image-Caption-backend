package com.example.imagecaptionbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.imagecaptionbackend.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact(
                "Sky-devourer",
                "http://localhost:8080/swagger-ui.html",
                "skydevourer@foxmail.com"
        );
        return new ApiInfoBuilder()
                .title("Image Caption Backend API")
                .description("图片描述项目后端接口文档")
                .version("1.0")
                .license("MIT")
                .licenseUrl("https://mit-license.org")
                .contact(contact)
                .build();
    }
}
