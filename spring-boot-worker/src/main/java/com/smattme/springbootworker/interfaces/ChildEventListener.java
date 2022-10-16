package com.smattme.springbootworker.interfaces;

import org.springframework.amqp.core.Message;

public interface ChildEventListener {

    void onMessage(Message message) throws RuntimeException;
}
