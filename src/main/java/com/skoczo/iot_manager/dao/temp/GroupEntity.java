package com.skoczo.iot_manager.dao.temp;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class GroupEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String name;
	
	@OneToMany
	private List<SensorEntity> sensors;
}
