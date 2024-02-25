package edu.java.bot.command;

import edu.java.bot.configuration.CommandConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartCommand implements Command {
    private final CommandConfig config;
    private static final String NAME = "/start";

    @Override
    public String command() {
        return NAME;
    }

    @Override
    public String description() {
        return config.getStart();
    }
}
