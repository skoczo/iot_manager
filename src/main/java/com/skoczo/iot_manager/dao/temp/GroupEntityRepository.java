package com.skoczo.iot_manager.dao.temp;

import org.springframework.data.repository.CrudRepository;

public interface GroupEntityRepository extends CrudRepository<GroupEntity, Long> {
	public Iterable<GroupEntity> findAllByUserId(Integer userId);
}
