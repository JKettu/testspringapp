package ru.rti.kettu.sbkotlin.configuration

import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerConfig.*
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG
import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.IntegerDeserializer
import org.apache.kafka.common.serialization.IntegerSerializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.listener.KafkaMessageListenerContainer
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer
import ru.rti.kettu.dbconnectorapi.model.OperationAlbumApi
import ru.rti.kettu.dbconnectorapi.model.OperationAlbumApiResponse
import java.util.*

@Configuration
class KafkaConfiguration {

    @Value("\${kafka.server}")
    lateinit var kafkaServer: String
    @Value("\${kafka.group.id}")
    lateinit var groupId: String

    //+
    @Bean
    fun consumerConfigs(): Map<String, Any> {
        val configs: MutableMap<String, Any> = HashMap()
        configs[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaServer
        configs[KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        configs[VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        configs[GROUP_ID_CONFIG] = groupId
        return configs
    }

    //+
    @Bean
    fun producerConfig(): Map<String, Any> {
        val configs = HashMap<String, Any>()
        configs[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaServer
        configs[KEY_SERIALIZER_CLASS_CONFIG] = IntegerSerializer::class.java
        configs[VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        configs[ProducerConfig.CLIENT_ID_CONFIG] = groupId
        return configs
    }

    //+
    @Bean
    fun producerFactory(): ProducerFactory<Int, OperationAlbumApi> {
        return DefaultKafkaProducerFactory(producerConfig())
    }

    /*@Bean
    fun kafkaTemplate(): KafkaTemplate<Int, Any> {
        val tmpl = KafkaTemplate(producerFactory())
        tmpl.setMessageConverter(StringJsonMessageConverter())
        return tmpl
    }*/

    //+
    @Bean
    fun topicAlbum(): NewTopic {
        return TopicBuilder.name("album")
                .partitions(4)
                .compact()
                .build()
    }

    //+
    @Bean
    fun albumConsumerFactory(): ConsumerFactory<Int, OperationAlbumApiResponse> {
        return DefaultKafkaConsumerFactory(consumerConfigs(), IntegerDeserializer(), albumJsonDeserializer())
    }

    //+
    @Bean
    fun replyAlbumKafkaTemplate(): ReplyingKafkaTemplate<Int, OperationAlbumApi, OperationAlbumApiResponse> {
        return ReplyingKafkaTemplate(producerFactory(), replyAlbumListenerContainer())
    }

    //+
    @Bean
    fun replyAlbumListenerContainer(): KafkaMessageListenerContainer<Int, OperationAlbumApiResponse> {
        val containerProperties = ContainerProperties("albumReply")
        return KafkaMessageListenerContainer(albumConsumerFactory(), containerProperties)
    }

    //+
    @Bean
    fun albumJsonDeserializer(): Deserializer<OperationAlbumApiResponse>? {
        val deserializer = JsonDeserializer(OperationAlbumApiResponse::class.java)
        deserializer.setRemoveTypeHeaders(false)
        deserializer.addTrustedPackages("*")
        deserializer.setUseTypeMapperForKey(true)
        return deserializer
    }

    @Bean
    fun albumSingleFactory(): ConcurrentKafkaListenerContainerFactory<Int, OperationAlbumApiResponse> {
        val factory = ConcurrentKafkaListenerContainerFactory<Int, OperationAlbumApiResponse>()
        factory.setConsumerFactory(albumConsumerFactory())
        factory.setReplyTemplate(replyAlbumKafkaTemplate())
        factory.isBatchListener = false
        return factory
    }
}