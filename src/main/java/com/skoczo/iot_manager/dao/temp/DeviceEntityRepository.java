package com.skoczo.iot_manager.dao.temp;

import org.springframework.data.repository.CrudRepository;

public interface DeviceEntityRepository extends CrudRepository<DeviceEntity, Integer> {

	public DeviceEntity findByDeviceId(String deviceId);
}
