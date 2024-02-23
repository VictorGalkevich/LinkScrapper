package edu.java.bot.processor;

import edu.java.bot.command.Command;
import edu.java.bot.command.TrackCommand;
import edu.java.bot.entity.Link;
import edu.java.bot.repository.UserRepository;
import edu.java.bot.tgbot.model.BotUpdate;
import edu.java.bot.tgbot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrackMessageProcessor extends UserLinkMessageProcessor {
    private final UserRepository userRepository;

    @Override
    public SendMessage process(Command command, BotUpdate update) {
        if (command instanceof TrackCommand) {
            Link link;
            Long id = update.id();
            try {
                link = retrieveLink(update);
            } catch (RuntimeException e) {
                return new SendMessage(id, getMessage(e, command));
            }
            return userRepository.findById(id).map((it) -> {
                if (isTracked(link, it)) {
                    return new SendMessage(id, "Link is already being tracked");
                } else {
                    it.getLinks().add(link);
                    return new SendMessage(id, "Link was added to tracking list!");
                }
            }).orElseThrow(() -> new RuntimeException("User not present"));
        } else {
            return null;
        }
    }
}
