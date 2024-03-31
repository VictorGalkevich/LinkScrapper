package edu.java.scrapper.rate_limit;

import edu.java.scrapper.configuration.ApplicationConfig;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RateLimitBucketCache {
    private final ApplicationConfig config;
    private Bandwidth bandwidth;

    @Cacheable(value = "rate-limit-buckets-cache", key = "#root.args[0]")
    public Bucket getBucket(String ip) {
        return build();
    }

    @PostConstruct
    void initBandWidth() {
        ApplicationConfig.RateLimit rateLimit = config.rateLimit();
        bandwidth = Bandwidth.classic(
            rateLimit.capacity(),
            Refill.greedy(
                rateLimit.refillRate(),
                Duration.ofSeconds(rateLimit.refillTimeSeconds())
            )
        );
    }

    private Bucket build() {
        return Bucket.builder()
            .addLimit(bandwidth)
            .build();
    }
}
