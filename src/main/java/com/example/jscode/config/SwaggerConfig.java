package com.example.jscode.config;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.spring.web.plugins.Docket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
@EnableWebMvc// spring-security와 연결할 때 이 부분이 없으면 404에러가 뜬다.
public class SwaggerConfig implements WebMvcConfigurer {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
    /**
     * Swagger2 버전은 http://localhost:8080/swagger-ui.html
     * spring-security와 연결할 때 이 부분을 작성하지 않으면 404에러가 뜬다.
     *
     * 3.x 버전 부터는 swagger-ui 경로가 다르다고 합니다.
     * http://localhost:8080/swagger-ui/index.html 로 접근해보세요
     */
}
