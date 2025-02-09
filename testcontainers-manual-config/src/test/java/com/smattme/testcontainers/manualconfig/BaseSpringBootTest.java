package com.smattme.testcontainers.manualconfig;


import com.smattme.testcontainers.manualconfig.config.WebTestClientConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@ContextConfiguration(classes = WebTestClientConfiguration.class)
@Slf4j
public class BaseSpringBootTest {


    static PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("testcontainers_demo")
                    .withInitScript("init.sql")
                    .withUsername("test")
                    .withExposedPorts(5432)
                    .withPassword("test");

    static GenericContainer redisContainer = new GenericContainer<>("redis:latest")
            .withExposedPorts(6379);


    @DynamicPropertySource
    static void registerDynamicProperties(DynamicPropertyRegistry registry) {

        postgresContainer.start();

        registry.add("spring.r2dbc.url", () -> String.format(
                "r2dbc:postgresql://%s:%d/%s",
                postgresContainer.getHost(),
                postgresContainer.getMappedPort(5432),
                postgresContainer.getDatabaseName()
        ));
        registry.add("spring.r2dbc.username", postgresContainer::getUsername);
        registry.add("spring.r2dbc.password", postgresContainer::getPassword);

        registry.add("spring.data.redis.host", redisContainer::getHost);
        registry.add("spring.data.redis.port", () -> redisContainer.getMappedPort(6379));

        Runtime.getRuntime().addShutdownHook(new Thread(postgresContainer::stop));
    }


}
