package com.skoczo.iot_manager.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemperatureController {
	@Autowired
	TemperatureEntityRepository repository;
	
	
	@PostMapping("/temperature")
	public void setTemperature(@RequestBody TemperatureEntity tempEnt) {
		System.out.println(tempEnt);
		repository.save(tempEnt);
		
	}
	
	@GetMapping("/temperatures")
	public List<TemperatureEntity> getTemperatures() {
		return repository.findAll();
	}

	@GetMapping("/temperature/{id}")
	public TemperatureEntity getTemperature(@PathVariable Long id) {
//		TemperatureEntity tmp = new TemperatureEntity();
//		tmp.setId(1L);
//		tmp.setSensorId("test_sensor");
//		tmp.setValue(44.2f);
		Optional<TemperatureEntity> entity = repository.findById(id);
		if(entity.isPresent()) {
			return entity.get();							
		} else {
			TemperatureEntity tempEnt = new TemperatureEntity();
			tempEnt.setId(-1);
			return tempEnt;
		}
		
	}
}
