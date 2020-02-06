package ru.rti.kettu.sbkotlin.configuration

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RabbitConfiguration {

    @Bean
    fun connectionFactory(): ConnectionFactory {
        return CachingConnectionFactory("localhost")
    }

    @Bean
    @Autowired
    fun amqpAdmin(connectionFactory: ConnectionFactory): AmqpAdmin {
        return RabbitAdmin(connectionFactory)
    }

    @Bean
    @Autowired
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        return RabbitTemplate(connectionFactory)
    }

    @Bean
    fun albumQueue(): Queue {
        return Queue("albumQueue")
    }

    @Bean
    fun songQueue(): Queue {
        return Queue("songQueue")
    }

    @Bean
    fun directExchange(): DirectExchange? {
        return DirectExchange("dbConnectorExchange")
    }

    @Bean
    fun albumBinding(): Binding {
        return BindingBuilder.bind(albumQueue()).to(directExchange()).with("album")
    }

    @Bean
    fun songBinding(): Binding {
        return BindingBuilder.bind(songQueue()).to(directExchange()).with("song")
    }
}