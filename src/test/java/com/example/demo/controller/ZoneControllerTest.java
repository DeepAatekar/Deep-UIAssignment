package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.example.demo.model.Zone;
import com.example.demo.service.ZoneService;

public class ZoneControllerTest
{
	@Mock
    private ZoneService zoneService;

    @InjectMocks
    private ZoneController zoneController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddZone() {
        // Arrange
        Zone zone = new Zone();
        zone.setId(1L);
        zone.setName("North Zone");

        when(zoneService.addZone(zone)).thenReturn(zone);

        // Act
        ResponseEntity<Zone> response = zoneController.addZone(zone);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(zone, response.getBody());

        verify(zoneService, times(1)).addZone(zone);
    }

    

    @Test
    void testGetZoneById() {
        // Arrange
        Long zoneId = 1L;
        Zone zone = new Zone();
        zone.setId(zoneId);
        zone.setName("North Zone");

        when(zoneService.getZoneById(zoneId)).thenReturn(zone);

        // Act
        ResponseEntity<Zone> response = zoneController.getZoneById(zoneId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(zone, response.getBody());

        verify(zoneService, times(1)).getZoneById(zoneId);
    }
}
