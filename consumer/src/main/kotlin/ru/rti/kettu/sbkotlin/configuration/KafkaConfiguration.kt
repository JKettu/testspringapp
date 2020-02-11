package ru.rti.kettu.sbkotlin.configuration

import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerConfig.*
import org.apache.kafka.common.serialization.IntegerSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.converter.StringJsonMessageConverter
import org.springframework.kafka.support.serializer.JsonSerializer
import java.util.*


@Configuration
class KafkaConfiguration {

    @Value("\${kafka.server}")
    lateinit var kafkaServer: String
    @Value("\${kafka.group.id}")
    lateinit var groupId: String

   /* @Bean
    fun consumerConfigs(): Map<String, Any> {
        val configs: MutableMap<String, Any> = HashMap()
        configs[BOOTSTRAP_SERVERS_CONFIG] = kafkaServer
        configs[KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        configs[VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        configs[GROUP_ID_CONFIG] = groupId
        return configs
    }*/

    @Bean
    fun producerConfig(): Map<String, Any> {
        val configs = HashMap<String, Any>()
        configs[BOOTSTRAP_SERVERS_CONFIG] = kafkaServer
        configs[KEY_SERIALIZER_CLASS_CONFIG] = IntegerSerializer::class.java
        configs[VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        configs[CLIENT_ID_CONFIG] = groupId
        return configs
    }

    @Bean
    fun producerFactory(): ProducerFactory<Int, Any> {
        return DefaultKafkaProducerFactory(producerConfig())
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<Int, Any> {
        val tmpl = KafkaTemplate(producerFactory())
        tmpl.setMessageConverter(StringJsonMessageConverter())
        return tmpl
    }

    /*@Bean
    fun replyConsumerFactory(): ConsumerFactory<Int, Any> {
        return DefaultKafkaConsumerFactory(consumerConfigs(), StringDeserializer(), JsonSerializer<Any>())
    }

    @Bean
    fun albumReplyListenerContainer(): KafkaMessageListenerContainer<Int, Any> {
        val containerProperties = ContainerProperties(topicSongReplay().name())
        return KafkaMessageListenerContainer(replyConsumerFactory(), containerProperties)
    }*/

    @Bean
    fun topicSong(): NewTopic {
        return TopicBuilder.name("album")
                .partitions(4)
                .compact()
                .build()
    }

   /* @Bean
    fun topicSongReplay(): NewTopic {
        return TopicBuilder.name("albumReply")
                .partitions(4)
                .compact()
                .build()
    }*/
}