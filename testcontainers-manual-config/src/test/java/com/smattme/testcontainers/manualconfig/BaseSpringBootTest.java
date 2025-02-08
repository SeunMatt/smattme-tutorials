package com.smattme.testcontainers.manualconfig;


import com.smattme.testcontainers.manualconfig.config.WebTestClientConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = WebTestClientConfiguration.class)
@Slf4j
public class BaseSpringBootTest {


}
