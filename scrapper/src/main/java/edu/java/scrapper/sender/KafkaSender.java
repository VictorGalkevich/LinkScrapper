package edu.java.scrapper.sender;

import edu.java.dto.request.LinkUpdate;
import edu.java.scrapper.configuration.ApplicationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class KafkaSender implements UpdateSender {
    private final ApplicationConfig config;
    private final KafkaTemplate<Long, LinkUpdate> kafkaTemplate;

    @Override
    public void sendUpdate(LinkUpdate request) {
        kafkaTemplate.send(config.kafkaConfigInfo().updatesTopic().name(), request.id(), request);
    }
}
