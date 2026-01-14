package com.dev.pontoeletronicoapi.config;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

@Configuration
@ConditionalOnProperty(name = "spring.flyway.enabled", havingValue = "true", matchIfMissing = true)
public class FlywayConfig {

    @Bean(initMethod = "migrate")
    @ConditionalOnMissingBean(Flyway.class)
    public Flyway flyway(DataSource dataSource, Environment environment) {
        String locationsValue = environment.getProperty("spring.flyway.locations", "classpath:db/migration");
        boolean baselineOnMigrate = environment.getProperty(
                "spring.flyway.baseline-on-migrate",
                Boolean.class,
                false
        );

        String[] locations = StringUtils.commaDelimitedListToStringArray(locationsValue);

        return Flyway.configure()
                .dataSource(dataSource)
                .locations(locations)
                .baselineOnMigrate(baselineOnMigrate)
                .load();
    }
}
