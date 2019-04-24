package com.skoczo.iot_manager.controllers.pub;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skoczo.iot_manager.security.token.web.users.User;
import com.skoczo.iot_manager.security.token.web.users.UserAuthenticationService;
import com.skoczo.iot_manager.security.token.web.users.UserCrudService;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.UUID;

@RestController
@RequestMapping("/public/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class PublicUsersController {
  @NonNull
  UserAuthenticationService authentication;
  @NonNull
  UserCrudService users;

  @PostMapping("/register")
  public String register(
    @RequestParam("username") final String username,
    @RequestParam("password") final String password) {
    users
      .save(
        User
          .builder()
          .id(username)
          .username(username)
          .password(password)
          .iotToken(UUID.randomUUID().toString())
          .build()
      );

    return login(username, password);
  }

  @PostMapping("/login")
  public String login(
    @RequestParam("username") final String username,
    @RequestParam("password") final String password) {
    return authentication
      .login(username, password)
      .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
  }
}
