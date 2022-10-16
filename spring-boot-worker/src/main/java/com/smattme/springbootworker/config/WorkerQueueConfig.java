package com.smattme.springbootworker.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "smattme.worker-config")
public class WorkerQueueConfig {

    private String[] queueNames;

    public String[] getQueueNames() {
        return queueNames;
    }

    public void setQueueNames(String[] queueNames) {
        this.queueNames = queueNames;
    }
}
