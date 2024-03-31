package edu.java.backoff.type;

import java.time.Duration;

public class ExponentialBackoff extends Backoff {
    public ExponentialBackoff(Duration duration) {
        super(duration);
    }

    @Override
    public Duration calculateWaitTime(int attempt) {
        return Duration.ofSeconds((long) Math.pow(constant.getSeconds(), attempt));
    }
}
