package ru.rti.kettu.sbjava.config;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.rti.kettu.dbconnectorapi.model.OperationAlbumApi;
import ru.rti.kettu.dbconnectorapi.model.OperationAlbumApiResponse;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Value("${kafka.server}")
    private String kafkaServer;

    @Value("${kafka.group.id}")
    private String kafkaGroupId;


    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(GROUP_ID_CONFIG, kafkaGroupId);
        props.put(ENABLE_AUTO_COMMIT_CONFIG, true);
        return props;
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        configs.put(KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        configs.put(VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configs.put(CLIENT_ID_CONFIG, kafkaGroupId);
        return configs;
    }

    @Bean
    public Deserializer<OperationAlbumApi> albumJsonDeserializer() {
        JsonDeserializer<OperationAlbumApi> deserializer = new JsonDeserializer<>(OperationAlbumApi.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);
        return deserializer;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Integer, OperationAlbumApi> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, OperationAlbumApi> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(albumConsumerFactory());
        factory.setReplyTemplate(replyAlbumKafkaTemplate());
        factory.setBatchListener(false);
        return factory;
    }

    @Bean
    public ConsumerFactory<Integer, OperationAlbumApi> albumConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new IntegerDeserializer(), albumJsonDeserializer());
    }

    @Bean
    public KafkaTemplate<Integer, OperationAlbumApiResponse> replyAlbumKafkaTemplate() {
        return new KafkaTemplate<>(albumProducerFactory());
    }

    @Bean
    public ProducerFactory<Integer, OperationAlbumApiResponse> albumProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }
}
