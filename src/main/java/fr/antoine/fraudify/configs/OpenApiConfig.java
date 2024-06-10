package fr.antoine.fraudify.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
            contact = @Contact(
                    name = "Antoine",
                    email = "Antwoine49@gmail.com"
            ),
            description = "Fraudify OpenApi documentation",
            title="Fraudify API",
            version = "1.0.0"
    ),
        servers = {
            @Server(
                    url = "http://localhost:8080",
                    description = "Local server"
            )
        }
)
public class OpenApiConfig {
}
