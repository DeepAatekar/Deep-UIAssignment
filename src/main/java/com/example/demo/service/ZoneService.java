package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Zone;
import com.example.demo.repository.ZoneRepository;

@Service
public class ZoneService
{
	@Autowired
	private ZoneRepository zoneRepository;
	
	public Zone addZone(Zone zone) 
	{
		
		return zoneRepository.save(zone);
	}
	
	public List<Zone> getAllZones()
	{
		return zoneRepository.findAll();
	}
	
	public Zone getZoneById(Long id) 
	{
		return zoneRepository.findById(id).orElse(null);
	}
}
