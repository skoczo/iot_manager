package com.skoczo.iot_manager.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skoczo.iot_manager.security.token.web.users.User;
import com.skoczo.iot_manager.security.token.web.users.UserCrudService;
import com.skoczo.iot_manager.utils.UserUtils;

@RestController("/security")
public class SecurityController {
	
	@Autowired
	private UserUtils userUtils;
	
	@Autowired
	UserCrudService users;

	@GetMapping("/resetIOTtoken")
	public String resetIOTToken() {
		User user = userUtils.getLoggedUser();
		user.setIotToken(UUID.randomUUID().toString());
		users.save(user);
		
		return user.getIotToken();
	}
}
