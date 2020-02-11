package ru.rti.kettu.sbjava.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.BatchMessagingMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.rti.kettu.dbconnectorapi.model.OperationAlbumApi;
import ru.rti.kettu.dbconnectorapi.model.OperationSongApi;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

@Configuration
public class KafkaConfig {

    @Value("${kafka.server}")
    private String kafkaServer;

    @Value("${kafka.group.id}")
    private String kafkaGroupId;

    @Bean
    public KafkaListenerContainerFactory<?> albumBatchFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, OperationAlbumApi> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(albumConsumerFactory());
        factory.setBatchListener(true);
        factory.setMessageConverter(new BatchMessagingMessageConverter(converter()));
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> albumSingleFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, OperationAlbumApi> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(albumConsumerFactory());
        factory.setBatchListener(false);
        factory.setMessageConverter(converter());
        return factory;
    }

    @Bean
    public ConsumerFactory<Integer, OperationSongApi> songConsumerFactory() {
        return new DefaultKafkaConsumerFactory<Integer, OperationSongApi>(consumerConfigs(), new IntegerDeserializer(), new JsonDeserializer(OperationSongApi.class));
    }

    @Bean
    public ConsumerFactory<Integer, OperationAlbumApi> albumConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new IntegerDeserializer(), albumJsonDeserializer());
    }

    @Bean
    public KafkaListenerContainerFactory<?> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, OperationAlbumApi> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(albumConsumerFactory());
        return factory;
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(GROUP_ID_CONFIG, kafkaGroupId);
        props.put(ENABLE_AUTO_COMMIT_CONFIG, true);
        return props;
    }

    @Bean
    public Deserializer albumJsonDeserializer() {
        JsonDeserializer<OperationAlbumApi> deserializer = new JsonDeserializer<>(OperationAlbumApi.class, false);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);
        return deserializer;
    }

    @Bean
    public StringJsonMessageConverter converter() {
        return new StringJsonMessageConverter();
    }
}
