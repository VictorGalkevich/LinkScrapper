package edu.java.scrapper.configuration;

import edu.java.dto.request.LinkUpdate;
import edu.java.scrapper.client.BotClient;
import edu.java.scrapper.sender.KafkaSender;
import edu.java.scrapper.sender.WebClientSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class UpdateSenderConfig {
    @Bean
    @ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
    public KafkaSender scrapperQueueProducer(
        ApplicationConfig config,
        KafkaTemplate<Long, LinkUpdate> kafkaTemplate
    ) {
        return new KafkaSender(config, kafkaTemplate);
    }

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
    public WebClientSender clientLinkUpdateSender(BotClient botClient) {
        return new WebClientSender(botClient);
    }
}
