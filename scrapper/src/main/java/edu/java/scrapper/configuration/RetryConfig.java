package edu.java.scrapper.configuration;

import edu.java.backoff.filter.RetryFilter;
import edu.java.backoff.properties.RetryProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RetryConfig {
    private final RetryPropertiesConfig config;

    @Bean
    public RetryFilter gitHubFilter() {
        RetryProperties github = config.getGithub();
        return build(github);
    }

    @Bean
    public RetryFilter stackOverflowFilter() {
        RetryProperties stackoverflow = config.getStackoverflow();
        return build(stackoverflow);
    }

    @Bean
    public RetryFilter botFilter() {
        RetryProperties bot = config.getBot();
        return build(bot);
    }


    private RetryFilter build(RetryProperties props) {
        return new RetryFilter(
            props.backoffType.getBackoff(props.minBackoff),
            props.statuses,
            props.attemptsThreshold
        );
    }
}
