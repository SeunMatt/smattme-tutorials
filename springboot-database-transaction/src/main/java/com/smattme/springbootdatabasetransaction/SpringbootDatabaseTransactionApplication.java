package com.smattme.springbootdatabasetransaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringbootDatabaseTransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDatabaseTransactionApplication.class, args);
    }


}
