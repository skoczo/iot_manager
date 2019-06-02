package com.skoczo.iot_manager.security.token.iot;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.skoczo.iot_manager.security.token.web.users.UserAuthenticationService;

@Service
public class IOTTokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
	@Autowired private UserAuthenticationService auth;

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
