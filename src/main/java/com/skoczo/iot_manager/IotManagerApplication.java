package com.skoczo.iot_manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.skoczo.iot_manager.dao.auth.AuthUser;
import com.skoczo.iot_manager.dao.auth.AuthUserRepository;

@SpringBootApplication
public class IotManagerApplication {
	private static final Logger log = LoggerFactory.getLogger(IotManagerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(IotManagerApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(AuthUserRepository repository) {
		return (args) -> {
			AuthUser user = new AuthUser();
			user.setUsername("test");
			user.setPassword("password");
			user.setIotToken("59C7FB4F07D9C55CC740CA4DD912EEDC");
			
			repository.save(user);
			
			for(AuthUser u : repository.findAll()) {
				log.info(u.toString());
			}
		};
	}
}

