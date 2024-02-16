package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.processor.UntrackMessageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UntrackCommand implements Command{
    private static final String DESCRIPTION = "stop tracking a link";
    private static final String NAME = "/untrack";
    private final UntrackMessageProcessor untrackMessageProcessor;

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
        return untrackMessageProcessor.process(update);
    }
}
