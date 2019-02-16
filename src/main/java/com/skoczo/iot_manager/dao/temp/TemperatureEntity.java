package com.skoczo.iot_manager.dao.temp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class TemperatureEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private long userId;
	private Float value;
	@ManyToOne
	private SensorEntity sensor;
	@ManyToOne
	private DeviceEntity device;
	private long timestamp;

	public TemperatureEntity(TemperatureRestReqEntity restEnt) {
		setValue(restEnt.getValue());
	}
}
