package com.skoczo.iot_manager.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.skoczo.iot_manager.dao.temp.TemperatureEntity;
import com.skoczo.iot_manager.dao.temp.TemperatureEntityRepository;
import com.skoczo.iot_manager.dao.temp.TemperatureRestReqEntity;

@RestController
public class TemperatureController {
	@Autowired
	TemperatureEntityRepository repository;
	
	@PostMapping("/temperature")
	public void setTemperature(@RequestBody TemperatureRestReqEntity tempRestEnt) {
		TemperatureEntity tempEnt = new TemperatureEntity(tempRestEnt);
		tempEnt.setUserId(1);
		tempEnt.setTimestamp(System.currentTimeMillis());
		
		System.out.println(tempEnt);
		repository.save(tempEnt);
	}
	
	@GetMapping("/temperatures")
	public Iterable<TemperatureEntity> getTemperatures() {
		return repository.findAll();
	}

	@GetMapping("/temperature/{id}")
	public TemperatureEntity getTemperature(@PathVariable Long id) {
		Optional<TemperatureEntity> entity = repository.findById(id);
		if(entity.isPresent()) {
			return entity.get();							
		} else {
			return null;
		}
		
	}
}
