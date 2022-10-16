package com.smattme.springbootworker;

import com.smattme.springbootworker.config.Constants;
import com.smattme.springbootworker.listeners.EmailEventListener;
import com.smattme.springbootworker.listeners.MainEventListener;
import com.smattme.springbootworker.listeners.SMSEventListener;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWorkerApplication.class, args);
	}

//	@Bean
//	Queue queue() {
//		return new Queue(Constants.SMS_QUEUE, true);
//	}
//
//	@Bean
//	TopicExchange exchange() {
//		return new TopicExchange(Constants.SMS_EXCHANGE);
//	}
//
//	@Bean
//	Binding binding(Queue queue, TopicExchange exchange) {
//		return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
//	}
//
//	@Bean
//	Queue emailQueue() {
//		return new Queue(Constants.EMAIL_QUEUE, true);
//	}
//
//	@Bean
//	TopicExchange emailExchange() {
//		return new TopicExchange(Constants.EMAIL_EXCHANGE);
//	}
//
//	@Bean
//	Binding emailBinding() {
//		return BindingBuilder.bind(emailQueue()).to(emailExchange()).with("foo.bar.#");
//	}








}
