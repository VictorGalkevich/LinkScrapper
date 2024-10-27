package edu.java.scrapper.scheduler;

import edu.java.dto.request.LinkUpdate;
import edu.java.scrapper.configuration.ApplicationConfig;
import edu.java.scrapper.processor.Processor;
import edu.java.scrapper.sender.UpdateSender;
import edu.java.scrapper.service.ChatService;
import edu.java.scrapper.service.LinkService;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private final ApplicationConfig config;
    private final UpdateSender updateSender;
    private final LinkService jooqLinkService;
    private final ChatService jooqChatService;
    private final List<Processor> processors;

    @Scheduled(fixedDelayString = "PT${app.scheduler.interval}")
    public void update() {
        jooqLinkService.findAllWithDelay(
            config.scheduler().linkCheckDelay()).forEach(link -> processors.forEach(proc -> {
            if (proc.supports(link)) {
                proc.process(link)
                    .filter(Objects::nonNull)
                    .map(upd -> new LinkUpdate(
                        link.getId(),
                        URI.create(link.getUri()),
                        upd,
                        jooqChatService.findAllChatsByLinkId(link.getId())
                    ))
                    .subscribe(updateSender::sendUpdate);
            }
        }));
    }
}
