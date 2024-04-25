package edu.java.backoff.type;

import java.time.Duration;

public enum BackoffType {
    CONSTANT,
    LINEAR,
    EXPONENTIAL;

    public Backoff getBackoff(Duration duration) {
        return switch (this) {
            case CONSTANT -> new ConstantBackoff(duration);
            case LINEAR -> new LinearBackoff(duration);
            case EXPONENTIAL -> new ExponentialBackoff(duration);
        };
    }
}
