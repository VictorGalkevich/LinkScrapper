package edu.java.bot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.entity.Link;
import edu.java.bot.entity.User;
import edu.java.bot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ListMessageProcessor implements UserMessageProcessor {
    private final UserRepository userRepository;
    private final String TEMPLATE = "<b>%s)</b> - %s\n";

    @Override
    public SendMessage process(Update update) {
        Long id = update.message().chat().id();
        List<Link> links = userRepository.findById(id)
            .map(User::getLinks)
            .orElseGet(ArrayList::new);
        StringBuilder message;
        if (!links.isEmpty()) {
            message = new StringBuilder("Tracked links: \n");
            int cnt = 1;
            for (Link link : links) {
                message.append(TEMPLATE.formatted(cnt++, link.getUri()));
            }
        } else {
            message = new StringBuilder("You are not tracking any links yet");
        }
        return new SendMessage(id, message.toString()).parseMode(ParseMode.HTML);
    }
}
