package com.skoczo.iot_manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skoczo.iot_manager.dao.temp.DeviceEntity;
import com.skoczo.iot_manager.dao.temp.DeviceEntityRepository;

@RestController
public class DeviceController {
	@Autowired
	private DeviceEntityRepository deviceRepository;
	
	@GetMapping("/devices")
	public Iterable<DeviceEntity> getAllDevices() {
		return deviceRepository.findAll();
	}
}
