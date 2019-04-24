package com.skoczo.iot_manager.controllers.users;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skoczo.iot_manager.security.token.web.users.User;
import com.skoczo.iot_manager.security.token.web.users.UserAuthenticationService;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class SecuredUsersController {
  @NonNull
  private UserAuthenticationService authentication;

  @GetMapping("/current")
  public User getCurrent(@AuthenticationPrincipal final User user) {
    return user;
  }

  @GetMapping("/logout")
  public boolean logout(@AuthenticationPrincipal final User user) {
    authentication.logout(user);
    return true;
  }
}
