package com.c123.KafkaSpring.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

@Service
public class ProducerImpl implements Producer {

    @Autowired
    KafkaTemplate<Long, String> kafkaTemplate;

    @Autowired
    Logger logger;

    @Value("${spring.kafka.topic}")
    private String topic;

    @Override
    public SendResult sendMessageSync(long key, String message) throws ExecutionException, InterruptedException {
        logger.debug(message);
        return kafkaTemplate.send(topic, key, message).get();
    }

    @Override
    public ListenableFuture sendMessageAsync(long key, String message) {
        logger.debug(message.toString());
        return kafkaTemplate.send(topic, key, message.toString());
    }
}
