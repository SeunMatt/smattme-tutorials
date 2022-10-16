package com.smattme.springbootworker.controllers;

import com.smattme.springbootworker.config.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    private final RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    public IndexController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/sms")
    public ResponseEntity<Void> sendSMSEvent() {
        rabbitTemplate.convertAndSend(Constants.SMS_EXCHANGE, "foo.bar.", Constants.SMS_QUEUE + " EVENT");
        logger.info("SMS Event sent");
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email")
    public ResponseEntity<Void> sendEmailEvent() {
        rabbitTemplate.convertAndSend(Constants.EMAIL_EXCHANGE, "foo.email.", Constants.EMAIL_QUEUE + " EVENT");
        logger.info("Email Event sent");
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/gcm")
    public ResponseEntity<Void> sendGcmEvent() {
        rabbitTemplate.convertAndSend(Constants.GCM_EXCHANGE, "foo.gcm.", Constants.GCM_QUEUE + " EVENT");
        logger.info("GCM Event sent");
        return ResponseEntity.noContent().build();
    }
}
