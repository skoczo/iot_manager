package com.skoczo.iot_manager.controllers;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class TemperatureEntity {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private Float value;
    private String sensorId;
    private String deviceId;

    public TemperatureEntity() {}
}
