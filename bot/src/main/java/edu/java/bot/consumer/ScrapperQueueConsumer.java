package edu.java.bot.consumer;

import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.service.UpdateService;
import edu.java.dto.request.LinkUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScrapperQueueConsumer {
    private final ApplicationConfig config;
    private final UpdateService updateService;
    private final KafkaTemplate<Long, LinkUpdate> kafkaTemplate;

    @KafkaListener(groupId = "scrapper.updates.listeners",
                   topics = "${app.kafka-config-info.updates-topic.name}",
                   containerFactory = "linkUpdateRequestConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload LinkUpdate request) {
        try {
            updateService.proceedUpdates(request);
        } catch (Exception e) {
            log.error(e.getMessage());
            kafkaTemplate.send(config.kafkaConfigInfo().updatesTopic().name() + "_dlq", request.id(), request);
        }
    }
}
