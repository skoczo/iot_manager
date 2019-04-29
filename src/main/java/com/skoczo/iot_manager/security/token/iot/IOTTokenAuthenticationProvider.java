package com.skoczo.iot_manager.security.token.iot;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.skoczo.iot_manager.security.token.web.users.UserAuthenticationService;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Component
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class IOTTokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
	@NonNull
	UserAuthenticationService auth;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		// nothing
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		final Object token = authentication.getCredentials();
	    return Optional
	      .ofNullable(token)
	      .map(String::valueOf)
	      .flatMap(auth::findByIOTToken)
	      .orElseThrow(() -> new UsernameNotFoundException("Bad IOT token"));
	  }

}
