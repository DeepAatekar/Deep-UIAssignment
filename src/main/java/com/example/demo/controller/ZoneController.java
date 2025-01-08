package com.example.demo.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Zone;
import com.example.demo.service.ZoneService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/zones")
public class ZoneController
{
	
	private static final Logger logger = LoggerFactory.getLogger(ZoneController.class);
	
	@Autowired
	private ZoneService zoneService;
	
	@PostMapping("/add")
	public ResponseEntity<Zone> addZone(@RequestBody Zone zone)
	{
		logger.info("Adding Zone: {}", zone);
		return ResponseEntity.ok(zoneService.addZone(zone));
	}
	
	
	@GetMapping
	public ResponseEntity<?> getAllZone()
	{
		return ResponseEntity.ok(zoneService.getAllZones());
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Zone> getZoneById(@PathVariable Long id)
	{
		return ResponseEntity.ok(zoneService.getZoneById(id));
	}
}
