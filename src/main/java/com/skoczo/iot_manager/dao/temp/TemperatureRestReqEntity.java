package com.skoczo.iot_manager.dao.temp;

import lombok.Data;

@Data
public class TemperatureRestReqEntity {
    private Float value;
    private String sensorId;
    private String deviceId;
}
