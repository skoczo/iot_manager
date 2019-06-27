package com.skoczo.iot_manager.security.token.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;
import com.skoczo.iot_manager.security.token.web.users.User;
import com.skoczo.iot_manager.security.token.web.users.UserAuthenticationService;
import com.skoczo.iot_manager.security.token.web.users.UserCrudService;

@Service
final class TokenAuthenticationService implements UserAuthenticationService {
  @Autowired TokenService tokens;
  @Autowired UserCrudService users;
  @Autowired
  private PasswordEncoder passwordEncoder;
  
  @Override
  public Optional<String> login(final String username, final String password) {	  
    return users
      .findByUsername(username)
      .filter(user -> passwordEncoder.matches(password, user.getPassword()))
      .map(user -> tokens.expiring(ImmutableMap.of("username", username)));
  }

  @Override
  public Optional<User> findByToken(final String token) {
    return Optional
      .of(tokens.verify(token))
      .map(map -> map.get("username"))
      .flatMap(users::findByUsername);
  }
  
  @Override
  public Optional<User> findByIOTToken(final String iotToken) {
    return users.findByIotToken(iotToken);
  }

  @Override
  public void logout(final User user) {
	// TODO: remove token
    // Nothing to do
  }
}