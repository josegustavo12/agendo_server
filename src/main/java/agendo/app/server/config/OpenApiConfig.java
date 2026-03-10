package agendo.app.server.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI agendoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Agendo API")
                        .description("API para gerenciamento de agendamentos entre profissionais e clientes.")
                        .version("0.0.1"));
    }
}
