package com.skoczo.iot_manager.dao.temp;

import org.springframework.data.repository.CrudRepository;

public interface DeviceEntityRepository extends CrudRepository<DeviceEntity, Long> {

	public DeviceEntity findByDeviceId(String deviceId);
}
