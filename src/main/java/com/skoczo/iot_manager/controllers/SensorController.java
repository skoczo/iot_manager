package com.skoczo.iot_manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.skoczo.iot_manager.dao.temp.SensorEntity;
import com.skoczo.iot_manager.dao.temp.SensorEntityRepository;

@RestController
public class SensorController {
	@Autowired
	private SensorEntityRepository sensorRepository;
	
	@GetMapping("/sensors")
	public Iterable<SensorEntity> getAllDevices() {
		// TODO find only logged user sensors 
		return sensorRepository.findAll();
	}
	
	@PatchMapping("/sensor/{sensorId}/{name}")
	public SensorEntity setSensorName(@PathVariable String sensorId, @PathVariable String name) {
		SensorEntity sensor = sensorRepository.findBySensorId(sensorId);
		//TODO: check if not managing other user sensor
		if(sensor != null) {
			sensor.setName(name);
			return sensorRepository.save(sensor);
		} else {
			return null;
			// TODO:
		}
	}
}
