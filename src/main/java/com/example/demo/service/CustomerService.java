package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Customer;
import com.example.demo.model.Zone;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ZoneRepository;

@Service
public class CustomerService 
{
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ZoneRepository zoneRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	public Customer registerCustomer(Customer customer) 
	{
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        //customer.setRoles("ROLE_USER");
        
        Long zoneId = customer.getZone().getId();
        Optional<Zone> zone = zoneRepository.findById(zoneId);
        if(zone.isPresent()) 
        {
        	customer.setZone(zone.get());
        }
        else 
        {
        	throw new RuntimeException("Zone with ID "+ zoneId +" not found");
        }
        
        return customerRepository.save(customer);
    }
	
	public Optional<Customer> loginCustomer(String email, String password)
	{
        Optional<Customer> customer = customerRepository.findByEmail(email);
        if (customer.isPresent() && passwordEncoder.matches(password, customer.get().getPassword())) {
            return customer;
        }
        return Optional.empty();
    }
	
	public String logoffCustomer(Long customerId)
	{
        return "Customer logged off successfully";
    }
	
	/*public int pointsCalculation(Double dollor) 
	{
		int points = 0;
		if(dollor > 100) 
		{
			points = points +(dollor.intValue()-100)*2;
		}
		if(dollor>50 && dollor<100) 
		{
			points = (int) (Math.min(dollor, 100)-50);
		}
		
		return points;
		
	}*/
	
	public List<Customer> getCustomerByZoneId(Long zoneId)
	{
		return customerRepository.findAllByZoneId(zoneId);
	}
}
