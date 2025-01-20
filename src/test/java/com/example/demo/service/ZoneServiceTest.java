package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.model.Zone;
import com.example.demo.repository.ZoneRepository;

public class ZoneServiceTest 
{
	@Mock
    private ZoneRepository zoneRepository;

    @InjectMocks
    private ZoneService zoneService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddZone_Success() {
        // Arrange
        Zone zone = new Zone();
        zone.setId(1L);
        zone.setName("North Zone");

        when(zoneRepository.save(zone)).thenReturn(zone);

        // Act
        Zone result = zoneService.addZone(zone);

        // Assert
        assertNotNull(result);
        assertEquals("North Zone", result.getName());
        verify(zoneRepository, times(1)).save(zone);
    }

    @Test
    void testGetAllZones_Success() {
        // Arrange
        Zone zone1 = new Zone();
        zone1.setId(1L);
        zone1.setName("North Zone");

        Zone zone2 = new Zone();
        zone2.setId(2L);
        zone2.setName("South Zone");

        when(zoneRepository.findAll()).thenReturn(List.of(zone1, zone2));

        // Act
        List<Zone> result = zoneService.getAllZones();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(zoneRepository, times(1)).findAll();
    }

    @Test
    void testGetZoneById_ExistingId() {
        // Arrange
        Zone zone = new Zone();
        zone.setId(1L);
        zone.setName("North Zone");

        when(zoneRepository.findById(1L)).thenReturn(Optional.of(zone));

        // Act
        Zone result = zoneService.getZoneById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("North Zone", result.getName());
        verify(zoneRepository, times(1)).findById(1L);
    }

    @Test
    void testGetZoneById_NonExistingId() {
        // Arrange
        when(zoneRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Zone result = zoneService.getZoneById(1L);

        // Assert
        assertNull(result);
        verify(zoneRepository, times(1)).findById(1L);
    }
}
