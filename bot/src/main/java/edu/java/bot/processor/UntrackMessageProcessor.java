package edu.java.bot.processor;

import edu.java.bot.client.ScrapperClient;
import edu.java.bot.command.Command;
import edu.java.bot.command.UntrackCommand;
import edu.java.bot.exception.ApiResponseException;
import edu.java.bot.tgbot.model.BotUpdate;
import edu.java.bot.tgbot.request.SendMessage;
import edu.java.dto.request.RemoveLinkRequest;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UntrackMessageProcessor extends UserLinkMessageProcessor {
    private final ScrapperClient client;

    @Override
    public SendMessage process(Command command, BotUpdate update) {
        if (command instanceof UntrackCommand) {
            URI link;
            Long id = update.id();
            try {
                link = retrieveLink(update);
            } catch (RuntimeException e) {
                return new SendMessage(id, getMessage(e, command));
            }
            String message = client.remove(id, new RemoveLinkRequest(link))
                    .map(resp -> "Link was removed from tracking list!")
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
