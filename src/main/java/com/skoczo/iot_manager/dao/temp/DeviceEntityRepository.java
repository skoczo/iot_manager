package com.skoczo.iot_manager.dao.temp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface DeviceEntityRepository extends CrudRepository<DeviceEntity, Long> {

	public DeviceEntity findByDeviceIdAndUserId(String deviceId, Integer userId);
	public List<DeviceEntity> findAllByUserId(Integer userId);
}
