package com.smattme.springboot.hmacsignature.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.filters.hmac")
public class HmacSignatureFilterConfig {

    private List<String> pathPrefix;
    private Long allowedTimeDiffInMillis;
}
