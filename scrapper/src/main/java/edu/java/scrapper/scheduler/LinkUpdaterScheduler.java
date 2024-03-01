package edu.java.scrapper.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LinkUpdaterScheduler {
    @Scheduled(fixedDelayString = "PT${app.scheduler.interval}")
    public void update() {
        log.info("Doing updates processing");
    }
}
