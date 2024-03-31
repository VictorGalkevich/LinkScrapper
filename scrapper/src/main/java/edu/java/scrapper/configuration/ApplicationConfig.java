package edu.java.scrapper.configuration;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app")
public record ApplicationConfig(
    @NotNull
    Scheduler scheduler,
    RateLimit rateLimit,
    @NotNull
    String gitHubToken

) {
    public record Scheduler(
        boolean enable,
        @NotNull Duration interval,
        @NotNull Duration forceCheckDelay,
        @NotNull Duration linkCheckDelay
    ) {
    }

    public record RateLimit(
        Long capacity,
        Long refillRate,
        Long refillTimeSeconds,
        Long cacheSize,
        Duration expireAfterAccess
    ) {
    }
}
