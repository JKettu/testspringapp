package ru.rti.kettu.sbjava.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean("rabbitConnection")
    public ConnectionFactory rabbitConnection() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    @Autowired
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    @Autowired
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public Queue albumQueue() {
        return new Queue("albumQueue");
    }

    @Bean
    public Queue songQueue() {
        return new Queue("songQueue");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("dbConnectorExchange");
    }

    @Bean
    public Binding albumBinding() {
        return BindingBuilder.bind(albumQueue()).to(directExchange()).with("album");
    }

    @Bean
    public Binding songBinding() {
        return BindingBuilder.bind(songQueue()).to(directExchange()).with("song");
    }
}
