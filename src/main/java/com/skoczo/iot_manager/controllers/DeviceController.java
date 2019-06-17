package com.skoczo.iot_manager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skoczo.iot_manager.dao.temp.DeviceEntity;
import com.skoczo.iot_manager.dao.temp.DeviceEntityRepository;
import com.skoczo.iot_manager.utils.UserUtils;

@RestController
public class DeviceController {
	private DeviceEntityRepository deviceRepository;
	private UserUtils userUtils;

	@Autowired
	public DeviceController(DeviceEntityRepository deviceRepository, 
			UserUtils userUtils) {
		this.deviceRepository = deviceRepository;
		this.userUtils = userUtils;
	}

	@GetMapping("/devices")
	public List<DeviceEntity> getAllDevices() {
		return deviceRepository.findAllByUserId(userUtils.getLoggedUser().getId());
	}
}
