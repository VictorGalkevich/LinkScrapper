package edu.java.bot.configuration;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MicrometerConfig {
    @Bean
    public Counter processedMessagesCounter(MeterRegistry registry, ApplicationConfig config) {
        return Counter.builder(config.micrometer().processedMessagesCounter().name())
            .description(config.micrometer().processedMessagesCounter().description())
            .register(registry);
    }
}
