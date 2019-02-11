package com.skoczo.iot_manager.dao.temp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class TemperatureEntity {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	private long userId;
    private Float value;
    private String sensorId;
    private String deviceId;
    private long timestamp;

    public TemperatureEntity(TemperatureRestReqEntity restEnt) 
    {
    	setSensorId(restEnt.getSensorId());
    	setDeviceId(restEnt.getDeviceId());
    	setValue(restEnt.getValue());
    }
}
