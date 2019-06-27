package com.skoczo.iot_manager.security.token.web.users;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UserCrudService extends CrudRepository<User, Integer>{
	Optional<User> findByUsername(String username);	
	Optional<User> findByIotToken(String iotToken);
}
