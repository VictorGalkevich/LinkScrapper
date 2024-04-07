package edu.java.scrapper.configuration;

import edu.java.dto.request.LinkUpdate;
import java.util.Map;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaConfiguration {
    @Bean
    public NewTopic scrapperUpdatesTopic(ApplicationConfig config) {
        return TopicBuilder
            .name(config.kafkaConfigInfo().updatesTopic().name())
            .partitions(config.kafkaConfigInfo().updatesTopic().partitions())
            .replicas(config.kafkaConfigInfo().updatesTopic().replicas())
            .build();
    }

    @Bean
    public ProducerFactory<Long, LinkUpdate> producerFactory(ApplicationConfig config) {
        return new DefaultKafkaProducerFactory<>(Map.of(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.kafkaConfigInfo().bootstrapServers(),
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
            JsonSerializer.ADD_TYPE_INFO_HEADERS, false
        ));
    }

    @Bean
    public KafkaTemplate<Long, LinkUpdate> kafkaTemplate(
        ProducerFactory<Long, LinkUpdate> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }
}
