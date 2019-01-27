package com.skoczo.iot_manager.controllers;

import org.springframework.data.jpa.repository.JpaRepository;

interface TemperatureEntityRepository extends JpaRepository<TemperatureEntity, Long>{

}
