package com.example.demo.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig
{
		@Autowired
		private CustomerDetailsService customerDetailsService;
		
	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    	return http
	            .csrf(csrf->csrf.disable())
	            .authorizeHttpRequests(auth->auth.requestMatchers("/customers/**","/swagger-ui/**","/v3/api-docs/**","/zones/**","/user/**").permitAll()
	            		.requestMatchers("/transactions/edit/**","/transactions/delete/**").hasRole("ADMIN")
	    	            .anyRequest().authenticated())
	         /*   .authorizeHttpRequests(auth -> auth
	                    .anyRequest().permitAll()) */
	            .httpBasic(Customizer.withDefaults())
	            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .build();
	    }

	    
	    
	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	    
	    @Bean
	    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder, CustomerDetailsService customerDetailsService) throws Exception 
	    {
	    	return http.getSharedObject(AuthenticationManagerBuilder.class)
	    			.userDetailsService(customerDetailsService)
	    			.passwordEncoder(passwordEncoder)
	    			.and()
	    			.build();
	    			
	    }
	   
}
