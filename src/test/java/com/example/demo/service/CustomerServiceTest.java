package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.model.Customer;
import com.example.demo.model.Zone;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ZoneRepository;

public class CustomerServiceTest 
{
	@Mock
    private CustomerRepository customerRepository;

    @Mock
    private ZoneRepository zoneRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterCustomer_Success() {
        // Arrange
        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPassword("password");
        Zone zone = new Zone();
        zone.setId(1L);
        zone.setName("North Zone");
        customer.setZone(zone);

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(zoneRepository.findById(1L)).thenReturn(Optional.of(zone));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // Act
        Customer result = customerService.registerCustomer(customer);

        // Assert
        assertNotNull(result);
        assertEquals("encodedPassword", result.getPassword());
        assertEquals(zone, result.getZone());

        verify(passwordEncoder, times(1)).encode("password");
        verify(zoneRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testRegisterCustomer_ZoneNotFound() {
        // Arrange
        Customer customer = new Customer();
        Zone zone = new Zone();
        zone.setId(2L);
        customer.setZone(zone);

        when(zoneRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> customerService.registerCustomer(customer));
        assertEquals("Zone with ID 2 not found", exception.getMessage());

        verify(zoneRepository, times(1)).findById(2L);
        verify(customerRepository, never()).save(any());
    }

    @Test
    void testLoginCustomer_Success() {
        // Arrange
        Customer customer = new Customer();
        customer.setEmail("john.doe@example.com");
        customer.setPassword("encodedPassword");

        when(customerRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(customer));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        // Act
        Optional<Customer> result = customerService.loginCustomer("john.doe@example.com", "password");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(customer, result.get());

        verify(customerRepository, times(1)).findByEmail("john.doe@example.com");
        verify(passwordEncoder, times(1)).matches("password", "encodedPassword");
    }

    @Test
    void testLoginCustomer_Failure() {
        // Arrange
        when(customerRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.empty());

        // Act
        Optional<Customer> result = customerService.loginCustomer("john.doe@example.com", "password");

        // Assert
        assertFalse(result.isPresent());

        verify(customerRepository, times(1)).findByEmail("john.doe@example.com");
        verify(passwordEncoder, never()).matches(any(), any());
    }

    @Test
    void testLogoffCustomer() {
        // Act
        String result = customerService.logoffCustomer(1L);

        // Assert
        assertEquals("Customer logged off successfully", result);
    }

    @Test
    void testGetCustomerByZoneId() {
        // Arrange
        Zone zone = new Zone();
        zone.setId(1L);
        
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("Deep");
        customer1.setEmail("deep@example.com");
        customer1.setPassword("password");
        customer1.setRoles("ROLE_USER");
        customer1.setZone(zone);
        
        Customer customer2 = new Customer();
        customer2.setId(1L);
        customer2.setName("Deep1");
        customer2.setEmail("deep1@example.com");
        customer2.setPassword("password");
        customer2.setRoles("ROLE_USER");
        customer2.setZone(zone);


        List<Customer> customers = Arrays.asList(customer1,customer2);

        when(customerRepository.findAllByZoneId(1L)).thenReturn(customers);

        // Act
        List<Customer> result = customerService.getCustomerByZoneId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getName());
        assertEquals("Jane", result.get(1).getName());

        verify(customerRepository, times(1)).findAllByZoneId(1L);
    }
}
