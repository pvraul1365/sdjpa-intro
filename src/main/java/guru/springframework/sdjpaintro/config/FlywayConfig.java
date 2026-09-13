package guru.springframework.sdjpaintro.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FlywayConfig
 */
@Configuration
public class FlywayConfig {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.flyway.user}")
    private String username;

    @Value("${spring.flyway.password}")
    private String password;

    @Value("${spring.flyway.locations}")
    private String locations;

    @Bean
    public Flyway flyway() {
        Flyway flyway = Flyway.configure()
                .dataSource(datasourceUrl, username, password)
                .locations(locations)
                .load();
        flyway.migrate();
        return flyway;
    }
}
