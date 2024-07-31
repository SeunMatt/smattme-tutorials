package com.smattme.micrometertracing.config;

import com.smattme.micrometertracing.listeners.DemoEventListener;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.ContainerCustomizer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConfig {

    @Value("${app.messaging.queueName}")
    private String inputQueueName;

    @Value("${app.messaging.exchangeName}")
    private String exchangeName;

    @Value("${app.messaging.routingKey}")
    private String inputRoutingKey;

    @Value("${app.messaging.output.queueName}")
    private String outputQueueName;

    @Value("${app.messaging.output.routingKey}")
    private String outputRoutingKey;

    private final DemoEventListener demoEventListener;

    public RabbitMQConfig(DemoEventListener demoEventListener) {
        this.demoEventListener = demoEventListener;
    }


    @Bean
    public Queue inputQueue() {
        return QueueBuilder.durable(inputQueueName).build();
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    Binding inputQueueBinding() {
        return BindingBuilder
                .bind(QueueBuilder.durable(inputQueueName).build())
                .to(new TopicExchange(exchangeName))
                .with(inputRoutingKey);
    }

    @Bean
    public Queue outputQueue() {
        return QueueBuilder.durable(outputQueueName).build();
    }



    @Bean
    Binding outputBinding(Queue outputQueue, DirectExchange exchange) {
        return BindingBuilder
                .bind(outputQueue).to(exchange)
                .with(outputRoutingKey);
    }

    @Bean
    Binding inputBinding(Queue inputQueue, DirectExchange exchange) {
        return BindingBuilder
                .bind(inputQueue).to(exchange)
                .with(inputRoutingKey);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setObservationEnabled(true);
        container.setQueueNames(inputQueueName);
        container.setMessageListener(demoEventListener);
        return container;
    }

    @Bean
    public ContainerCustomizer<SimpleMessageListenerContainer> containerCustomizer() {
           return container -> container.setObservationEnabled(true);
    }







}
