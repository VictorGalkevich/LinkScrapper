package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.processor.HelpMessageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HelpCommand implements Command {
    private static final String DESCRIPTION = "show available commands";
    private final List<Command> commands;
    private static final String NAME = "/help";
    private final HelpMessageProcessor helpMessageProcessor;

    @Override
    public String command() {
        return NAME;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }

    @Override
    public SendMessage handle(Update update) {
        commands.add(this);
        helpMessageProcessor.setCommands(commands);
        return helpMessageProcessor.process(update);
    }
}
