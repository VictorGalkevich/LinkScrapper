package edu.java.bot.service;

import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.formatter.Formatter;
import edu.java.bot.tgbot.Bot;
import edu.java.bot.tgbot.request.SendMessage;
import edu.java.dto.request.LinkUpdate;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateService {
    private final Bot bot;
    private final Formatter formatter;
    private final ApplicationConfig config;

    public void proceedUpdates(LinkUpdate update) {
        List<Long> chats = update.tgChatIds();
        for (Long chat : chats) {
            String description = update.description();
            URI url = update.url();
            String message = config.updateMessage().formatted(
                    url.toString(),
                    description
            );
            SendMessage notification = new SendMessage(chat, message);
            bot.execute(notification.parseMode(formatter.parseMode()));
        }
    }
}
