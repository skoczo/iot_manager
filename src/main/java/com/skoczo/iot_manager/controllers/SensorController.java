package com.skoczo.iot_manager.controllers;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.skoczo.iot_manager.dao.temp.DeviceEntity;
import com.skoczo.iot_manager.dao.temp.DeviceEntityRepository;
import com.skoczo.iot_manager.dao.temp.SensorEntity;
import com.skoczo.iot_manager.dao.temp.SensorEntityRepository;
import com.skoczo.iot_manager.utils.UserUtils;

@RestController
public class SensorController {
	@Autowired
	private SensorEntityRepository sensorRepository;

	@Autowired
	private DeviceEntityRepository deviceRepository;

	@Autowired
	private UserUtils userUtils;

	@GetMapping("/sensors")
	public Iterable<SensorEntity> getAllDevices() {
		// TODO find only logged user sensors
		return sensorRepository.findAll();
	}

	@PatchMapping("/sensor/{sensorId}/{name}")
	public SensorEntity setSensorName(@PathVariable String sensorId, @PathVariable String name) {
		SensorEntity sensor = sensorRepository.findBySensorId(sensorId);
		// TODO: check if not managing other user sensor
		if (Objects.nonNull(sensor.getDeviceId())) {
			Optional<DeviceEntity> device = deviceRepository.findByDeviceId(sensor.getDeviceId());
			if (device.isPresent() && !device.get().getUserId().equals(userUtils.getLoggedUser().getId())) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Group belong to other user");
			}
		}

		if (sensor != null) {
			sensor.setName(name);
			return sensorRepository.save(sensor);
		} else {
			return null;
			// TODO:
		}
	}
}
