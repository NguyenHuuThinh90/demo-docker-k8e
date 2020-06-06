package com.example.docker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class KafkaController {

    @Resource
    private KafkaTemplate kafkaTemplate;

    @GetMapping(value = "/test/{msg}")
    public ResponseEntity test(@PathVariable String msg) {
        Message<String> msgTopic = MessageBuilder.withPayload(msg)
                .setHeader(KafkaHeaders.TOPIC, "test")
                .setHeader(KafkaHeaders.MESSAGE_KEY, "key")
                .setHeader(KafkaHeaders.PARTITION_ID, 0)
                .build();
        Message<String> msgTopicTemp = MessageBuilder.withPayload("part 2")
                .setHeader(KafkaHeaders.TOPIC, "test")
                .setHeader(KafkaHeaders.MESSAGE_KEY, "key1")
                .setHeader(KafkaHeaders.PARTITION_ID, 0)
                .build();
        Message<String> msgTopic1 = MessageBuilder.withPayload("topic1")
                .setHeader(KafkaHeaders.TOPIC, "test1")
                .setHeader(KafkaHeaders.MESSAGE_KEY, "key2")
                .build();

        kafkaTemplate.send(msgTopic);
        kafkaTemplate.send(msgTopicTemp);
        kafkaTemplate.send(msgTopic1);

        return ResponseEntity.ok().body("ok");
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "test", partitions = {"0"}))
    public void listenA(String message, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        System.out.println(key);
        System.out.println("Received Messasge partitions 1" + message);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "test", partitions = {"2"}))
    public void listenAA(String message, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        System.out.println(key);
        System.out.println("Received Messasge partitions 2" + message);
    }

    @KafkaListener(topicPattern = "test1")
    public void listenB(String message, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        System.out.println(key);
        System.out.println("Received test1:" + message);
    }
}
