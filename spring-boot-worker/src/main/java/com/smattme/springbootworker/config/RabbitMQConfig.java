package com.smattme.springbootworker.config;

import com.smattme.springbootworker.listeners.MainEventListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private final WorkerQueueConfig workerQueueConfig;

    public RabbitMQConfig(WorkerQueueConfig workerQueueConfig) {
        this.workerQueueConfig = workerQueueConfig;
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(workerQueueConfig.getQueueNames());
        container.setMessageListener(new MainEventListener());
        return container;
    }
}
