package edu.java.scrapper.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaffeineConfig {

    @Bean
    public Caffeine caffeine(ApplicationConfig applicationConfig) {
        return Caffeine.newBuilder()
            .expireAfterAccess(applicationConfig.rateLimit().expireAfterAccess())
            .maximumSize(applicationConfig.rateLimit().cacheSize());
    }

    @Bean
    public CacheManager manager(Caffeine caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }
}
