package edu.java.bot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.entity.Link;
import edu.java.bot.entity.User;
import edu.java.bot.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import static edu.java.bot.util.LinkUtil.isValid;
import static edu.java.bot.util.LinkUtil.parse;

@Component
@RequiredArgsConstructor
public class TrackMessageProcessor implements UserMessageProcessor {
    private final UserRepository userRepository;

    @Override
    public SendMessage process(Update update) {
        Long id = update.message().chat().id();
        String message;
        String[] s = update.message().text().split(" ");
        if (s.length != 2) {
            message = "/track syntax might be [/track <link>]";
        } else if (!isValid(s[1])) {
            message = "Provided link is invalid";
        } else if (isTracked(parse(s[1]), id)) {
            message = "Link is already being tracked";
        } else {
            Optional<User> user = userRepository.findById(id);
            user.ifPresent(it -> it.getLinks().add(parse(s[1])));
            message = "Link was added to tracking list!";
        }
        return new SendMessage(id, message);
    }

    private boolean isTracked(Link link, Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(User::getLinks)
            .map(it -> it.contains(link))
            .orElse(false);
    }
}
