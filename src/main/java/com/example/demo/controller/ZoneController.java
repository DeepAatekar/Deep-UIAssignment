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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/zones")
@Tag(name = "Zone API", description = "Endpoints for managing zones")
public class ZoneController
{
	
	private static final Logger logger = LoggerFactory.getLogger(ZoneController.class);
	
	@Autowired
	private ZoneService zoneService;
	
	
	@Operation(summary = "Add a new zone", description = "Adds a new zone with name and other details.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Zone added successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input data")
	})
	@PostMapping("/add")
	public ResponseEntity<Zone> addZone(@RequestBody(description = "Details of the zone to be added") Zone zone)
	{
		logger.info("Adding Zone: {}", zone);
		return ResponseEntity.ok(zoneService.addZone(zone));
	}
	
	
	@Operation(summary = "Get zone by ID", description = "Fetches details of a specific zone by its ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Zone fetched successfully"),
			@ApiResponse(responseCode = "404", description = "Zone not found")
	})
	@GetMapping
	public ResponseEntity<?> getAllZone()
	{
		return ResponseEntity.ok(zoneService.getAllZones());
	}
	
	
	
	@Operation(summary = "Get zone by ID", description = "Fetches details of a specific zone by its ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Zone fetched successfully"),
			@ApiResponse(responseCode = "404", description = "Zone not found")
	})
	@GetMapping("/{id}")
	public ResponseEntity<Zone> getZoneById(@PathVariable Long id)
	{
		return ResponseEntity.ok(zoneService.getZoneById(id));
	}
}
