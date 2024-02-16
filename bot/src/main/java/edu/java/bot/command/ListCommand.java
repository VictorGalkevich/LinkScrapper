package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.processor.ListMessageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ListCommand implements Command{
    private static final String DESCRIPTION = "show all tracked links";
    private static final String NAME = "/list";
    private final ListMessageProcessor listMessageProcessor;

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
        return listMessageProcessor.process(update);
    }
}
