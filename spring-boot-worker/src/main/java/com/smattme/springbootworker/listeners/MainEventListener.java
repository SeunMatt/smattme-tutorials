package com.smattme.springbootworker.listeners;

import com.smattme.springbootworker.config.SpringContext;
import com.smattme.springbootworker.interfaces.ChildEventListener;
import io.vavr.control.Try;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import static com.smattme.springbootworker.config.Constants.*;

public class MainEventListener implements MessageListener {

    @Override
    public void onMessage(Message message) {

        var consumerQueue = message.getMessageProperties().getConsumerQueue();

        //get the child event listener base on the consumer queue identified
        Class<? extends ChildEventListener> childEventListenerClass = switch (consumerQueue) {
            case SMS_QUEUE -> SMSEventListener.class;
            case EMAIL_QUEUE -> EmailEventListener.class;
            default -> null;
        };

        //get the bean instance of the childListener. It fails if none is found.
        ChildEventListener childListener = Try.of(() -> SpringContext.getBean(childEventListenerClass))
                .getOrElseThrow(throwable -> new RuntimeException("There's no child event listener for queue: " + consumerQueue));
        childListener.onMessage(message);
    }


}
