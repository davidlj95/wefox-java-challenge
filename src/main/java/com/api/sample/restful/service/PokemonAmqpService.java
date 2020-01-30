package com.api.sample.restful.service;

import com.api.sample.restful.helpers.JSONService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PokemonAmqpService {

    private final RabbitTemplate rabbitTemplate;
    private final AmqpAdmin amqpAdmin;
    private final Queue pokemonQueue;
    private final JSONService jsonService;
    private final PokemonService pokemonService;
    private final Integer RESPONSE_TIMEOUT = 2000;

    public Object addQueryToQueue(String name) {

        // Create queue to receive the response, and declare it as replyTo
        // in the message properties.
        Queue replyQueue = amqpAdmin.declareQueue();
        MessageProperties properties = new MessageProperties();
        properties.setReplyTo(replyQueue.getName());
        Message message = new Message(
                name.getBytes(),
                properties
        );

        // Send the query
        rabbitTemplate.send(pokemonQueue.getName(), message);

        // Await for the response
        Message response = rabbitTemplate.receive(
                replyQueue.getName(), RESPONSE_TIMEOUT
        );

        // Decode the JSON and return the response
        try {
            return jsonService.fromBytes(response.getBody());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @RabbitListener(queues = {"pokemon-query"})
    public void consumeQueryFromQueue(Message message) {
        // Receive the query and the queue where to reply
        String name = new String(message.getBody());
        String replyTo = message.getMessageProperties().getReplyTo();

        // Actually fetch the pokemons
        List<HashMap<String, Object>> pokemons =
                pokemonService.findByNameStartsWith(name);

        // Encode the pokemons in JSON and send them to the reply queue
        byte[] messageBody;
        try {
            messageBody = jsonService.toBytes(pokemons);
        } catch (JsonProcessingException e) {
            messageBody = "[]".getBytes();
        }
        rabbitTemplate.convertAndSend(replyTo, messageBody);
    }
}
