package com.skoczo.iot_manager.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skoczo.iot_manager.dao.temp.GroupEntity;
import com.skoczo.iot_manager.dao.temp.GroupEntityRepository;
import com.skoczo.iot_manager.dao.temp.SensorEntity;
import com.skoczo.iot_manager.dao.temp.SensorEntityRepository;
import com.skoczo.iot_manager.security.token.web.users.User;

@RestController
public class GroupController {
	@Autowired
	private GroupEntityRepository groupRepository;
	
	@Autowired
	private SensorEntityRepository sensorRepository;
		
	@GetMapping("/groups")
	public Iterable<GroupEntity> allGroups() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		
		return groupRepository.findAllByUserId(user.getId());
	}
	
	@PostMapping("/group/{groupName}")
	public GroupEntity addSensorToGroup(@PathVariable String groupName) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		
		GroupEntity group = new GroupEntity();
		group.setName(groupName);
		group.setUserId(user.getId());
		
		return groupRepository.save(group);
	}
	
	@PostMapping("/group/{groupId}/add/{sensorId}")
	public GroupEntity addSensorToGroup(@PathVariable Long groupId, @PathVariable String sensorId) throws Exception {
		SensorEntity sensor = sensorRepository.findBySensorId(sensorId);
		// TODO: check if not managing other users group
		Optional<GroupEntity> group = groupRepository.findById(groupId);
		if(group.isPresent() && sensor != null) {
			group.get().getSensors().add(sensor);
			return groupRepository.save(group.get());
		} else {
			throw new Exception("Sensor or group not found");
		}
	}
	
	@DeleteMapping("/group/{groupId}/delete/{sensorId}")
	public void removeSensorFromGroup(@PathVariable Long groupId, @PathVariable String sensorId) throws Exception {
		SensorEntity sensor = sensorRepository.findBySensorId(sensorId);
		// TODO: check if not managing other users group
		Optional<GroupEntity> group = groupRepository.findById(groupId);
		if(group.isPresent() && sensor != null) {
			GroupEntity groupEntity = group.get();
			groupEntity.getSensors().remove(sensor);
			groupRepository.save(groupEntity);
		} else {
			throw new Exception("Sensor or group not found");
		}
	}
}
