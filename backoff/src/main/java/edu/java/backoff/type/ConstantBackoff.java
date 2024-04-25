package edu.java.backoff.type;

import java.time.Duration;

public class ConstantBackoff extends Backoff {
    public ConstantBackoff(Duration constant) {
        super(constant);
    }

    @Override
    public Duration calculateWaitTime(int attempt) {
        return constant;
    }
}
