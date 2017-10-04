package com.decisionwanted.domain.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@PropertySource(value = "classpath:application.properties")
@ComponentScan("com.decisionwanted")
@EnableScheduling
@SpringBootApplication
public class Application {

	static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		logger.info("Starting application...");
		SpringApplication.run(Application.class, args);
	}

}