package edu.java.scrapper.rate_limit;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {
    private final RateLimitBucketCache cache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        Bucket tokenBucket = cache.getBucket(request.getRemoteAddr());
        ConsumptionProbe probe = tokenBucket.tryConsumeAndReturnRemaining(1);

        if (probe.isConsumed()) {
            long rem = probe.getRemainingTokens();
            response.addHeader(
                "X-Rate-Limit-Remaining",
                String.valueOf(rem)
            );
            return true;
        }

        Duration wait = Duration.ofNanos(
            probe.getNanosToWaitForRefill()
        );

        response.addHeader(
            "X-Rate-Limit-Retry-After-Seconds",
            String.valueOf(wait.toSeconds())
        );

        response.sendError(
            HttpStatus.TOO_MANY_REQUESTS.value(),
            "Too many requests were made"
        );
        return false;
    }
}
