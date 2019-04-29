package com.skoczo.iot_manager.security.token.web.users;

import java.util.Optional;

/**
 * User security operations like login and logout, and CRUD operations on
 * {@link User}.
 * 
 * @author jerome
 *
 */
public interface UserCrudService {

	User save(User user);

	Optional<User> find(String id);

	Optional<User> findByUsername(String username);
	
	Optional<User> findByIotToken(String iotToken);
}
