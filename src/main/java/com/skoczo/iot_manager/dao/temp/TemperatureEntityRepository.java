package com.skoczo.iot_manager.dao.temp;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TemperatureEntityRepository extends CrudRepository<TemperatureEntity, Long> {

	List<TemperatureEntity> findBySensor(SensorEntity sensor);
	
	List<TemperatureEntity> findBySensorAndTimestampGreaterThan(SensorEntity sensor, Long timestamp);

	@Query("select t from #{#entityName} t where t.timestamp >= :from and t.timestamp <= :to and t.sensor.id = :sensorId")
	List<TemperatureEntity> findTemperaturesInTimeframe(@Param("sensorId") String sensorId, @Param("from") Long from, @Param("to") Long to);
	
	public TemperatureEntity findFirstBySensorOrderByTimestampDesc(SensorEntity sensor);
}
