package edu.java.backoff.type;

import java.time.Duration;

public class LinearBackoff extends Backoff {
    public LinearBackoff(Duration constant) {
        super(constant);
    }

    @Override
    public Duration calculateWaitTime(int attempt) {
        return constant.multipliedBy(attempt);
    }
}
