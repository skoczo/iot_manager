package com.skoczo.iot_manager.dao.temp;

import org.springframework.data.repository.CrudRepository;

public interface SensorEntityRepository extends CrudRepository<SensorEntity, Integer> {

	public SensorEntity findBySensorId(String sensorId);
}
