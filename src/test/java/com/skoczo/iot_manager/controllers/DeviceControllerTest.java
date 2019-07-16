package com.skoczo.iot_manager.controllers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.skoczo.iot_manager.dao.temp.DeviceEntity;
import com.skoczo.iot_manager.dao.temp.DeviceEntityRepository;
import com.skoczo.iot_manager.security.token.web.users.User;
import com.skoczo.iot_manager.utils.UserUtils;

@RunWith(MockitoJUnitRunner.class)
public class DeviceControllerTest {

	@Mock
	private DeviceEntityRepository deviceRepository;

	@Mock
	private UserUtils userUtils;

	DeviceController deviceController;

	@Before
	public void setUp() {
		deviceController = new DeviceController(deviceRepository, userUtils);
	}

	@Test
	public void getAllDevicesTest() {
		List<DeviceEntity> devicesPerUser = new ArrayList<DeviceEntity>();
		DeviceEntity dev = new DeviceEntity();
		dev.setDeviceId("sddd");
		dev.setUserId(1);
		devicesPerUser.add(dev);
		Mockito.when(deviceRepository.findAllByUserId(Mockito.eq(1))).thenReturn(devicesPerUser);
		User user = new User();
		user.setId(1);
		Mockito.when(userUtils.getLoggedUser()).thenReturn(user);
		List<DeviceEntity> devices = deviceController.getAllDevices();
		
		Mockito.verify(deviceRepository, Mockito.times(1)).findAllByUserId(Mockito.any());
		Assert.assertEquals(1, devices.size());		
	}
}
