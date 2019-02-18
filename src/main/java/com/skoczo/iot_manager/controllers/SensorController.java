package com.skoczo.iot_manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skoczo.iot_manager.dao.temp.SensorEntity;
import com.skoczo.iot_manager.dao.temp.SensorEntityRepository;

@RestController
public class SensorController {
	@Autowired
	private SensorEntityRepository sensorRepository;
	
	@GetMapping("/sensors")
	public Iterable<SensorEntity> getAllDevices() {
		return sensorRepository.findAll();
	}
}
