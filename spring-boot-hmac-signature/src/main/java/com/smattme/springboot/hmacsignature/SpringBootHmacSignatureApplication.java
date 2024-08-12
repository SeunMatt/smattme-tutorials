package com.smattme.springboot.hmacsignature;

import db.callback.FlywayAwareDatabaseSeeder;
import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootHmacSignatureApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHmacSignatureApplication.class, args);
	}


	@Bean
	public FlywayMigrationStrategy flywayMigrationStrategy() {
		return (flywayOld) -> {

			/*
			 Update the existing autoconfigured Flyway
			 bean to include our callback class
			*/
			Flyway.configure()
					.configuration(flywayOld.getConfiguration())
					.callbacks(new FlywayAwareDatabaseSeeder())
					.load()
					.migrate();

		};
	}


}
