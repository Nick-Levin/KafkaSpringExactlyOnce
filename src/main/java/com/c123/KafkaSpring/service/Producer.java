package com.c123.KafkaSpring.service;

import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

public interface Producer {

    SendResult sendMessageSync(long key, String message) throws ExecutionException, InterruptedException;
    ListenableFuture sendMessageAsync(long key, String message);

}
