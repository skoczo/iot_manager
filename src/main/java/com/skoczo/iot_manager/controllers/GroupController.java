package com.skoczo.iot_manager.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skoczo.iot_manager.dao.temp.GroupEntity;
import com.skoczo.iot_manager.dao.temp.GroupEntityRepository;
import com.skoczo.iot_manager.dao.temp.SensorEntity;
import com.skoczo.iot_manager.dao.temp.SensorEntityRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class GroupController {
	@Autowired
	private GroupEntityRepository groupRepository;
	
	@Autowired
	private SensorEntityRepository sensorRepository;
		
	@GetMapping("/groups")
	public Iterable<GroupEntity> allGroups() {
		return groupRepository.findAll();
	}
	
	@PostMapping("/group/{groupName}")
	public GroupEntity addSensorToGroup(@PathVariable String groupName) {
		GroupEntity group = new GroupEntity();
		group.setName(groupName);
		
		return groupRepository.save(group);
	}
	
	@PostMapping("/group/{groupId}/add/{sensorId}")
	public GroupEntity addSensorToGroup(@PathVariable Long groupId, @PathVariable String sensorId) throws Exception {
		SensorEntity sensor = sensorRepository.findBySensorId(sensorId);
		
		Optional<GroupEntity> group = groupRepository.findById(groupId);
		if(group.isPresent() && sensor != null) {
			group.get().getSensors().add(sensor);
			return groupRepository.save(group.get());
		} else {
			throw new Exception("Sensor or group not found");
		}
	}
}
