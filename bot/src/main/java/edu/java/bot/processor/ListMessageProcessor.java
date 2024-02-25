package edu.java.bot.processor;

import edu.java.bot.command.Command;
import edu.java.bot.command.ListCommand;
import edu.java.bot.entity.Link;
import edu.java.bot.entity.User;
import edu.java.bot.formatter.Formatter;
import edu.java.bot.repository.UserRepository;
import edu.java.bot.tgbot.model.BotUpdate;
import edu.java.bot.tgbot.request.SendMessage;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class ListMessageProcessor extends UserMessageProcessor {
    private final UserRepository userRepository;
    private final String list;
    private final Formatter formatter;

    @Override
    public SendMessage process(Command command, BotUpdate update) {
        if (command instanceof ListCommand) {
            Long id = update.id();
            List<Link> links = userRepository.findById(id)
                    .map(User::getLinks)
                    .orElseGet(ArrayList::new);
            StringBuilder message;
            if (!links.isEmpty()) {
                message = new StringBuilder("Tracked links: \n");
                int cnt = 1;
                for (Link link : links) {
                    message.append(list.formatted(cnt++, link.getUri()));
                }
            } else {
                message = new StringBuilder("You are not tracking any links yet");
            }
            return new SendMessage(id, message.toString()).parseMode(formatter.parseMode());
        } else {
            return null;
        }
    }
}
