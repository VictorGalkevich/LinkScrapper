package edu.java.bot.command;

import edu.java.bot.configuration.CommandConfig;
import org.junit.jupiter.api.BeforeEach;

public abstract class CommandTest {
    protected CommandConfig config;

    @BeforeEach
    void init() {
        config = new CommandConfig();
    }
}
