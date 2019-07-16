package com.skoczo.iot_manager.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.skoczo.iot_manager.security.token.web.users.User;

@Service
public class UserUtils {
	public User getLoggedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (User) authentication.getPrincipal();
	}
	
	public boolean checkIfUserIsPermitted(Integer userId) {
		return getLoggedUser().getId().equals(userId);
	}
}
