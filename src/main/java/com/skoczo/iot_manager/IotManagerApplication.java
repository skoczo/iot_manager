package com.skoczo.iot_manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
public class IotManagerApplication {
	private static final Logger log = LoggerFactory.getLogger(IotManagerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(IotManagerApplication.class, args);
	}
}
