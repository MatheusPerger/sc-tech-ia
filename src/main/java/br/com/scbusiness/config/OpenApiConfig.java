package br.com.scbusiness.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SC Business API")
                        .version("1.0.0")
                        .description("""
                                REST API for managing businesses registered in Santa Catarina.
                                Built as part of the SCTEC - IA para DEVs selection challenge.
                                
                                ## Features
                                - Full CRUD for business management
                                - Filter by status and segment
                                - Bean Validation on all inputs
                                - Global exception handling
                                """)
                        .contact(new Contact()
                                .name("SC Business API")
                                .email("matheusperger@gmail.com")));
    }
}
