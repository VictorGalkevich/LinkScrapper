package edu.java.bot.processor;

import edu.java.bot.client.ScrapperClient;
import edu.java.bot.command.Command;
import edu.java.bot.command.StartCommand;
import edu.java.bot.exception.ApiResponseException;
import edu.java.bot.tgbot.model.BotUpdate;
import edu.java.bot.tgbot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class StartMessageProcessor extends UserMessageProcessor {
    private final ScrapperClient client;

    @Override
    public SendMessage process(Command command, BotUpdate update) {
        if (command instanceof StartCommand) {
            Long id = update.id();
            String message = client.register(id)
                .map(resp -> "Welcome, %s, use /help to see the help page".formatted(update.username()))
                .onErrorResume(
                    ApiResponseException.class,
                    err -> Mono.just(err.getResponse().description())
                )
                .block();
            return new SendMessage(id, message);
        } else {
            return null;
        }
    }
}
