package com.api.sample.restful.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AmqpConfig {

    private final AmqpAdmin amqpAdmin;

    @Bean
    Queue pokemonQueryQueue() {
        Queue queue = QueueBuilder.nonDurable("pokemon-query").build();
        amqpAdmin.declareQueue(queue);
        return queue;
    }
}
