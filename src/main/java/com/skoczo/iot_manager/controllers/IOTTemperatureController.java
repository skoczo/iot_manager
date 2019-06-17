package com.skoczo.iot_manager.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.skoczo.iot_manager.dao.temp.DeviceEntity;
import com.skoczo.iot_manager.dao.temp.DeviceEntityRepository;
import com.skoczo.iot_manager.dao.temp.SensorEntity;
import com.skoczo.iot_manager.dao.temp.SensorEntityRepository;
import com.skoczo.iot_manager.dao.temp.TemperatureEntity;
import com.skoczo.iot_manager.dao.temp.TemperatureEntityRepository;
import com.skoczo.iot_manager.dao.temp.TemperatureRestReqEntity;
import com.skoczo.iot_manager.security.token.web.users.User;
import com.skoczo.iot_manager.utils.UserUtils;

@RestController
@RequestMapping("/iot")
public class IOTTemperatureController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TemperatureEntityRepository tempRepository;

	@Autowired
	private DeviceEntityRepository devRpository;

	@Autowired
	private SensorEntityRepository sensorRepository;

	@Autowired
	private UserUtils userUtils;

	@PostMapping("/temperature")
	public void setTemperature(@RequestBody TemperatureRestReqEntity tempRestEnt) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		logger.info("user {} pushing temp entity", user.getUsername());

		DeviceEntity device = devRpository.findByDeviceIdAndUserId(tempRestEnt.getDeviceId(),
				userUtils.getLoggedUser().getId());
		if (device == null) {
			device = new DeviceEntity();
			device.setDeviceId(tempRestEnt.getDeviceId());
			device.setUserId(user.getId());
			device = devRpository.save(device);
		} else if (!device.getUserId().equals(user.getId())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Device belong to other user");
		}

		SensorEntity sensor = sensorRepository.findBySensorId(tempRestEnt.getSensorId());
		if (sensor == null) {
			sensor = new SensorEntity();
			sensor.setSensorId(tempRestEnt.getSensorId());
			sensor = sensorRepository.save(sensor);
		}

		TemperatureEntity tempEnt = new TemperatureEntity(tempRestEnt);
		tempEnt.setUserId(1);
		tempEnt.setDevice(device);
		tempEnt.setSensor(sensor);
		tempEnt.setTimestamp(System.currentTimeMillis());

		logger.info(tempEnt.toString());
		tempRepository.save(tempEnt);
	}
}
