package edu.java.backoff.type;

import java.time.Duration;

public abstract class Backoff {
    protected Duration constant;

    protected Backoff(Duration constant) {
        this.constant = constant;
    }

    public abstract Duration calculateWaitTime(int attempt);
}
