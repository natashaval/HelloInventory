package hello.inven.helloinven.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static springfox.documentation.builders.PathSelectors.regex;

//https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
//http://www.springboottutorial.com/spring-boot-swagger-documentation-for-rest-services

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
//                .apis(RequestHandlerSelectors.basePackage("package hello.inven.helloinven.controller"))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
//                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
//                .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
    }

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<String>(Arrays.asList("application/json"));

    private ApiInfo apiInfo(){
//        https://dzone.com/articles/spring-boot-2-restful-api-documentation-with-swagg
        return new ApiInfoBuilder()
                .title("HelloInven REST API")
                .description("Hello Inventory Management System REST API")
                .version("1.0.0")
                .contact(new Contact("Hello Inventory", "http://www.helloinven.com", "helloinven@helloinven.com"))
                .build();
    }
}

