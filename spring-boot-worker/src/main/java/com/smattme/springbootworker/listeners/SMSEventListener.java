package com.smattme.springbootworker.listeners;

import com.smattme.springbootworker.interfaces.ChildEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SMSEventListener implements ChildEventListener {

    private static final Logger logger = LoggerFactory.getLogger(SMSEventListener.class);


    public void onMessage(Message message) {
        logger.info("SMS Event Received at {} with message: {}", LocalDateTime.now(), message);
    }
}
