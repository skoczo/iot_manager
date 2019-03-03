package com.skoczo.iot_manager.controllers;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.skoczo.iot_manager.dao.temp.DeviceEntity;
import com.skoczo.iot_manager.dao.temp.DeviceEntityRepository;
import com.skoczo.iot_manager.dao.temp.SensorEntity;
import com.skoczo.iot_manager.dao.temp.SensorEntityRepository;
import com.skoczo.iot_manager.dao.temp.TemperatureEntity;
import com.skoczo.iot_manager.dao.temp.TemperatureEntityRepository;
import com.skoczo.iot_manager.dao.temp.TemperatureRestReqEntity;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TemperatureController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TemperatureEntityRepository tempRepository;

	@Autowired
	private DeviceEntityRepository devRpository;
	
	@Autowired
	private SensorEntityRepository sensorRepository;

	@PostMapping("/temperature")
	public void setTemperature(@RequestBody TemperatureRestReqEntity tempRestEnt) {
    	DeviceEntity device = devRpository.findByDeviceId(tempRestEnt.getDeviceId());
    	if(device == null) {
    		device = new DeviceEntity();
    		device.setDeviceId(tempRestEnt.getDeviceId());
    		device = devRpository.save(device);
    	}
    	
    	SensorEntity sensor = sensorRepository.findBySensorId(tempRestEnt.getSensorId());
    	if(sensor == null) {
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
	public Iterable<TemperatureEntity> getTemperaturesToday() {
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
		
		return tempRepository.findTodayTemperatures(today.getTimeInMillis());
	}
	
	@GetMapping("/temperatures/{from}/{to}")
	public Iterable<TemperatureEntity> getTemperaturesTimeframe(@PathVariable Long from, @PathVariable Long to) {		
		return tempRepository.findTemperaturesInTimeframe(from, to);
	}
}
