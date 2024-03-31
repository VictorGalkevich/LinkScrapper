package edu.java.scrapper.configuration;

import edu.java.scrapper.rate_limit.RateLimitBucketCache;
import edu.java.scrapper.rate_limit.RateLimitInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class RateLimitConfiguration implements WebMvcConfigurer {
    private final RateLimitBucketCache rateLimitBucketCache;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RateLimitInterceptor(rateLimitBucketCache))
            .addPathPatterns("/**");
    }
}
