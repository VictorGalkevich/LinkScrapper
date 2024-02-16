package edu.java.bot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.Command;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Setter
public class HelpMessageProcessor implements UserMessageProcessor {
    private List<Command> commands;
    private final String TEMPLATE = "<b>%s</b> - %s\n";

    @Override
    public SendMessage process(Update update) {
        StringBuilder message = new StringBuilder("Available commands: \n");
        for (Command command : commands) {
            message.append(TEMPLATE.formatted(command.command(), command.description()));
        }
        return new SendMessage(update.message().chat().id(), message.toString())
                .parseMode(ParseMode.HTML);
    }
}
