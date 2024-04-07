package edu.java.scrapper.sender;

import edu.java.dto.request.LinkUpdate;
import edu.java.scrapper.client.BotClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WebClientSender implements UpdateSender {
    private final BotClient botClient;

    @Override
    public void sendUpdate(LinkUpdate update) {
        botClient.sendUpdate(update)
            .subscribe();
    }
}
