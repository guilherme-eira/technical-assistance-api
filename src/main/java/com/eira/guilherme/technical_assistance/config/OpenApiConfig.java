package com.eira.guilherme.technical_assistance.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Technical Assistance API",
                version = "1.0.0",
                description = "API para gerenciar serviços de assistência técnica, permitindo o controle de técnicos, clientes, equipamentos, serviços prestados e ordens de serviço",
                contact = @Contact(
                        name = "Guilherme Eira",
                        url = "https://github.com/guilherme-eira",
                        email = "guilhermeaugustoeira@gmail.com"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        )
)
public class OpenApiConfig {
}