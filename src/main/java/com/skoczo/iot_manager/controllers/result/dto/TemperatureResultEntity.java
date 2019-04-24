package com.skoczo.iot_manager.controllers.result.dto;

import java.util.List;

import com.skoczo.iot_manager.dao.temp.SensorEntity;
import com.skoczo.iot_manager.dao.temp.TemperatureEntity;

import lombok.Data;

@Data
public class TemperatureResultEntity {
	private SensorEntity sensor;
	private List<TemperatureEntity> temperatures;
}
