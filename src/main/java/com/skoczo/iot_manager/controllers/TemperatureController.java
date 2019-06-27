package com.skoczo.iot_manager.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.skoczo.iot_manager.controllers.result.dto.TemperatureResultEntity;
import com.skoczo.iot_manager.dao.temp.DeviceEntityRepository;
import com.skoczo.iot_manager.dao.temp.SensorEntity;
import com.skoczo.iot_manager.dao.temp.SensorEntityRepository;
import com.skoczo.iot_manager.dao.temp.TemperatureEntity;
import com.skoczo.iot_manager.dao.temp.TemperatureEntityRepository;

@RestController
@RequestMapping("/data")
public class TemperatureController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TemperatureEntityRepository tempRepository;

	@Autowired
	private DeviceEntityRepository devRpository;
	
	@Autowired
	private SensorEntityRepository sensorRepository;

	@GetMapping("/temperatures")
	public Iterable<TemperatureEntity> getTemperatures() {
		return tempRepository.findAll();
	}

	@GetMapping("/temperatures/sensor/{sensorId}")
	public Iterable<TemperatureEntity> getTemperature(@PathVariable String sensorId) {
		SensorEntity sensor = sensorRepository.findBySensorId(sensorId);
		return tempRepository.findBySensor(sensor);
	}
	
	@GetMapping("/temperatures/sensor/{sensorId}/current")
	public TemperatureEntity getCurrentTemperature(@PathVariable String sensorId) {
		SensorEntity sensor = sensorRepository.findBySensorId(sensorId);
		return tempRepository.findFirstBySensorOrderByTimestampDesc(sensor);
	}
	
	@GetMapping("/temperatures/today")
	public List<TemperatureResultEntity> getTemperaturesToday(@PathParam("sensorIds") String[] sensorIds) {
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
		
		if(Objects.isNull(sensorIds)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "sensorIds parameter is null");
		}
		
		List<TemperatureResultEntity> result = new ArrayList<>();
		
		for(String sensorId : sensorIds) {
			TemperatureResultEntity resEntity = new TemperatureResultEntity();
			
			SensorEntity sensor = sensorRepository.findBySensorId(sensorId);
			resEntity.setSensor(sensor);
			resEntity.setTemperatures(tempRepository.findBySensorAndTimestampGreaterThan(sensor, today.getTimeInMillis()));
			result.add(resEntity);
		}
		return result;
	}
	
	@GetMapping("/temperatures/{sensorId}/{from}/{to}")
	public Iterable<TemperatureEntity> getTemperaturesTimeframe(@PathVariable String sensorId, @PathVariable Long from, @PathVariable Long to) {		
		return tempRepository.findTemperaturesInTimeframe(sensorId,from, to);
	}
}
