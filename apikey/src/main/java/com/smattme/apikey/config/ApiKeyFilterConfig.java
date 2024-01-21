package com.smattme.apikey.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.filters.apikey")
public class ApiKeyFilterConfig {

    private String pathPrefix;
}
