package com.skoczo.iot_manager.controllers.pub;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.skoczo.iot_manager.IotManagerApplication;
import com.skoczo.iot_manager.login.LoginAttemptService;
import com.skoczo.iot_manager.security.token.web.users.User;
import com.skoczo.iot_manager.security.token.web.users.UserAuthenticationService;
import com.skoczo.iot_manager.security.token.web.users.UserCrudService;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/public/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class PublicUsersController {
	private static final Logger log = LoggerFactory.getLogger(IotManagerApplication.class);

	@NonNull
	UserAuthenticationService authentication;
	@NonNull
	@Autowired
	UserCrudService users;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private LoginAttemptService loginAttemptService;

	@PostMapping("/register")
	public String register(@RequestParam("username") final String username,
			@RequestParam("password") final String password) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setIotToken(UUID.randomUUID().toString());

		users.save(user);

		return login(username, password);
	}

	@PostMapping("/login")
	public String login(@RequestParam("username") final String username,
			@RequestParam("password") final String password) {
		String ip = getClientIP();
		if (loginAttemptService.isBlocked(ip)) {
			throw new ResponseStatusException(HttpStatus.LOCKED, "ip blocked");
		}

		Optional<String> loginData = authentication.login(username, password);

		if (loginData.isPresent()) {
			loginAttemptService.loginSucceeded(ip);
			log.info("successfull login from ip: {}", ip);
			return loginData.get();
		}

		loginAttemptService.loginFailed(ip);
		log.error("invalid login to {} from ip: {}", username, ip);
		throw new ResponseStatusException(HttpStatus.FORBIDDEN, "invalid login and/or password");
	}

	private String getClientIP() {
		String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader == null) {
			return request.getRemoteAddr();
		}
		return xfHeader.split(",")[0];
	}
}
