package com.skoczo.iot_manager.controllers;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.skoczo.iot_manager.dao.temp.GroupEntity;
import com.skoczo.iot_manager.dao.temp.GroupEntityRepository;
import com.skoczo.iot_manager.dao.temp.SensorEntity;
import com.skoczo.iot_manager.dao.temp.SensorEntityRepository;
import com.skoczo.iot_manager.utils.UserUtils;

@RestController
public class GroupController {
	@Autowired
	private GroupEntityRepository groupRepository;

	@Autowired
	private SensorEntityRepository sensorRepository;

	@Autowired
	private UserUtils userUtils;

	@GetMapping("/groups")
	public Iterable<GroupEntity> allGroups() {
		return groupRepository.findAllByUserId(userUtils.getLoggedUser().getId());
	}

	@PostMapping("/group/{groupName}")
	public GroupEntity createGroup(@PathVariable String groupName) {
		GroupEntity group = new GroupEntity();
		group.setName(groupName);
		group.setUserId(userUtils.getLoggedUser().getId());

		return groupRepository.save(group);
	}

	/**
	 * 
	 * @param groupId   group id
	 * @param groupName group name to set
	 * @return saved group entity
	 */
	@PostMapping("/group/{groupId}/rename/{groupName}")
	public GroupEntity renameGroup(@PathVariable Long groupId, @PathVariable String groupName) {
		Optional<GroupEntity> groupOptional = groupRepository.findById(groupId);

		if (groupOptional.isPresent()) {
			GroupEntity group = groupOptional.get();
			if (userUtils.checkIfUserIsPermitted(group.getUserId())) {
				group.setName(groupName);
				return groupRepository.save(group);
			} else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Group belong to other user");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "group not found");
		}
	}

	@PostMapping("/group/{groupId}/add/{sensorId}")
	public GroupEntity addSensorToGroup(@PathVariable Long groupId, @PathVariable String sensorId)  {
		SensorEntity sensor = sensorRepository.findBySensorId(sensorId);

		Optional<GroupEntity> group = groupRepository.findById(groupId);
		if (group.isPresent() && Objects.nonNull(sensor)) {
			if (group.get().getUserId().equals(userUtils.getLoggedUser().getId())) {
				group.get().getSensors().add(sensor);
				return groupRepository.save(group.get());
			} else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Group belong to other user");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sensor or group not found");
		}
	}

	@DeleteMapping("/group/{groupId}/delete/{sensorId}")
	public void removeSensorFromGroup(@PathVariable Long groupId, @PathVariable String sensorId)  {
		SensorEntity sensor = sensorRepository.findBySensorId(sensorId);

		Optional<GroupEntity> group = groupRepository.findById(groupId);
		if (group.isPresent() && sensor != null) {
			if (group.get().getUserId().equals(userUtils.getLoggedUser().getId())) {
				GroupEntity groupEntity = group.get();
				groupEntity.getSensors().remove(sensor);
				groupRepository.save(groupEntity);
			} else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sensor belong to other user");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sensor or group not found");
		}
	}
}
