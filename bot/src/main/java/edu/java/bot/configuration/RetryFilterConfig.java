package edu.java.bot.configuration;

import edu.java.backoff.filter.RetryFilter;
import edu.java.backoff.properties.RetryProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RetryFilterConfig {
    private final RetryPropertiesConfig config;

    @Bean
    public RetryFilter filter() {
        RetryProperties retry = config.getProperties();
        return new RetryFilter(
            retry.backoffType.getBackoff(retry.minBackoff),
            retry.statuses,
            retry.attemptsThreshold
        );
    }
}
