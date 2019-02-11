package com.skoczo.iot_manager.dao.auth;

import org.springframework.data.repository.CrudRepository;

public interface AuthUserRepository extends CrudRepository<AuthUser, Integer>{
    AuthUser findByUsername(String name);
    AuthUser findByIotToken(String token);
}
