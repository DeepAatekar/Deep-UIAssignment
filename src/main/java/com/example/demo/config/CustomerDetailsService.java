package com.example.demo.config;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;

@Service
public class CustomerDetailsService implements UserDetailsService
{
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Customer customer=customerRepository.findByEmail(email)
				.orElseThrow(()-> new UsernameNotFoundException("User not found"));
		
		List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(customer.getRoles()));
		
		return new org.springframework.security.core.userdetails.User(
				customer.getEmail(),
				customer.getPassword(),
				authorities
				
				);
	}

}
