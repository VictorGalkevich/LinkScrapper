package edu.java.backoff.properties;

import edu.java.backoff.type.BackoffType;
import java.time.Duration;
import java.util.Set;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.BAD_GATEWAY;
import static org.springframework.http.HttpStatus.GATEWAY_TIMEOUT;
import static org.springframework.http.HttpStatus.INSUFFICIENT_STORAGE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@SuppressWarnings("MagicNumber")
public class RetryProperties {
    public Integer attemptsThreshold = 3;
    public Duration minBackoff = Duration.ofSeconds(20);
    public BackoffType backoffType = BackoffType.CONSTANT;
    public Set<HttpStatus> statuses = Set.of(
        INTERNAL_SERVER_ERROR,
        BAD_GATEWAY,
        INSUFFICIENT_STORAGE,
        SERVICE_UNAVAILABLE,
        GATEWAY_TIMEOUT);
}
