package com.smattme.micrometertracing.listeners;

import com.smattme.micrometertracing.services.DemoConsumerService;
import io.micrometer.tracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DemoEventListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(DemoEventListener.class);

    private final DemoConsumerService demoConsumerService;
    private final Tracer tracer;

    public DemoEventListener(DemoConsumerService demoConsumerService, Tracer tracer) {
        this.demoConsumerService = demoConsumerService;
        this.tracer = tracer;
    }


//    @RabbitListener(queues = "${app.messaging.queueName}")
    public void onMessage(Message message) {
        logger.info("New Event Received in DemoEventListener: {}", message);
        demoConsumerService.processDemoEvent(new String(message.getBody()));
    }
}
