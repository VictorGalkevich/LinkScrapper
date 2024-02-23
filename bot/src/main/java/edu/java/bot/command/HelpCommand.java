package edu.java.bot.command;

import edu.java.bot.configuration.CommandConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HelpCommand implements Command {
    private final CommandConfig config;
    private static final String NAME = "/help";

    @Override
    public String command() {
        return NAME;
    }

    @Override
    public String description() {
        return config.getHelp();
    }
}
