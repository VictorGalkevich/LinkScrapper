package edu.java.bot.processor;

import edu.java.bot.client.ScrapperClient;
import edu.java.bot.command.Command;
import edu.java.bot.command.ListCommand;
import edu.java.bot.exception.ApiResponseException;
import edu.java.bot.formatter.Formatter;
import edu.java.bot.tgbot.model.BotUpdate;
import edu.java.bot.tgbot.request.SendMessage;
import edu.java.dto.response.LinkResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ListMessageProcessor extends UserMessageProcessor {
    private final String list;
    private final Formatter formatter;
    private final ScrapperClient client;

    @Override
    public SendMessage process(Command command, BotUpdate update) {
        if (command instanceof ListCommand) {
            Long id = update.id();
            String message = client.getAllLinks(id)
                    .map(resp -> createMessage(resp.getBody().links()))
                    .onErrorResume(
                            ApiResponseException.class,
                            err -> Mono.just(err.getResponse().description())
                    )
                    .block();
            return new SendMessage(id, message).parseMode(formatter.parseMode());
        } else {
            return null;
        }
    }

    private String createMessage(List<LinkResponse> responses) {
        StringBuilder message;
        if (!responses.isEmpty()) {
            message = new StringBuilder("Tracked links: \n");
            int cnt = 1;
            List<String> links = responses.stream()
                    .map(LinkResponse::url)
                    .map(String::valueOf)
                    .toList();
            for (String link : links) {
                message.append(list.formatted(cnt++, link));
            }
        } else {
            message = new StringBuilder("You are not tracking any links yet");
        }
        return message.toString();
    }
}
