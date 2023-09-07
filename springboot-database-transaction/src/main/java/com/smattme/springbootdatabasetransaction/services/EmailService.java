package com.smattme.springbootdatabasetransaction.services;

import com.smattme.springbootdatabasetransaction.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendWelcomeEmail(User user) {
        logger.info("Email Sent");
    }

}
