package edu.java.bot.configuration;

import edu.java.dto.request.LinkUpdate;
import java.util.Map;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@EnableKafka
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
    public NewTopic scrapperUpdatesDlqTopic(ApplicationConfig config) {
        return TopicBuilder
            .name(config.kafkaConfigInfo().updatesTopic().name() + "_dlq")
            .partitions(config.kafkaConfigInfo().updatesTopic().partitions())
            .replicas(config.kafkaConfigInfo().updatesTopic().replicas())
            .build();
    }

    @Bean
    public ConsumerFactory<Long, LinkUpdate> linkUpdateRequestConsumerFactory(ApplicationConfig config) {
        return new DefaultKafkaConsumerFactory<>(Map.of(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.kafkaConfigInfo().bootstrapServers(),
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class,
            JsonDeserializer.TRUSTED_PACKAGES, "*",
            JsonDeserializer.USE_TYPE_INFO_HEADERS, false,
            JsonDeserializer.VALUE_DEFAULT_TYPE, LinkUpdate.class
        ));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, LinkUpdate>
    linkUpdateRequestConcurrentKafkaListenerContainerFactory(ConsumerFactory<Long, LinkUpdate> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Long, LinkUpdate> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ProducerFactory<Long, LinkUpdate> linkUpdateRequestproducerFactory(ApplicationConfig config) {
        return new DefaultKafkaProducerFactory<>(Map.of(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.kafkaConfigInfo().bootstrapServers(),
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
            JsonSerializer.ADD_TYPE_INFO_HEADERS, false
        ));
    }

    @Bean
    public KafkaTemplate<Long, LinkUpdate> linkUpdateRequestKafkaTemplate(
        ProducerFactory<Long, LinkUpdate> longLinkUpdateRequestProducerFactory
    ) {
        return new KafkaTemplate<>(longLinkUpdateRequestProducerFactory);
    }
}
