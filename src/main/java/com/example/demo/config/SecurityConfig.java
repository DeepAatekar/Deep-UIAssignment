package com.example.demo.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    	return http
	            .csrf(csrf->csrf.disable())
	            .authorizeHttpRequests(auth->auth.requestMatchers("/customers/**","/swagger-ui/**","/v3/api-docs/**","/zones/**","/user/**").permitAll()
	            		.requestMatchers("/transactions/edit/**","/transactionsdelete/**").hasRole("ADMIN")
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
	    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) throws Exception 
	    {
	    	return http.getSharedObject(AuthenticationManagerBuilder.class)
	    			.userDetailsService(userDetailsService)
	    			.passwordEncoder(passwordEncoder)
	    			.and()
	    			.build();
	    			
	    }
	   
}
