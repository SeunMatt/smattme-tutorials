package com.smattme.micrometertracing.services;

import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DemoConsumerService {

    @Value("${app.messaging.exchangeName}")
    private String exchangeName;

    @Value("${app.messaging.output.routingKey}")
    private String outputRoutingKey;

    @Value("${app.messaging.queueName}")
    private String inputQueueName;

    private final RabbitMessagingTemplate rabbitMessagingTemplate;

    private static final Logger logger = LoggerFactory.getLogger(DemoConsumerService.class);


    public DemoConsumerService(RabbitMessagingTemplate rabbitMessagingTemplate) {
        this.rabbitMessagingTemplate = rabbitMessagingTemplate;
    }

    public void processDemoEvent(String payload) {
        logger.info("Processing Demo Even with Payload: {}", payload);
        Try.of(() -> {Thread.sleep(1000 * 5); return null;}); //simulate processing delay
        rabbitMessagingTemplate.convertAndSend(exchangeName, outputRoutingKey, payload.concat("_OUTPUT"));
    }


}
