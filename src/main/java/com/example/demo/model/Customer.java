package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Schema(description = "Represent customer in the system")
public class Customer 
{
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Schema(description = "Unique identifier for the customer", example = "1")
	    private Long id;
	    
	    
	    @Schema(description = "Name of the customer", example = "Deep Aatekar")
	    private String name;

	    @Email(message = "Email should be valid")
	    @NotBlank(message = "Email is mandatory")
	    @Schema(description = "Email address of the customer", example = "deep.aatekar@example.com", required = true)
	    private String email;

	    
	    @NotBlank(message = "Password is mandatory")
	    @Schema(description = "Password for the customer's account", example = "password123", required = true)
	    private String password;

	    @Schema(description = "Role of the customer in the system", example = "ROLE_USER")
	    private String roles;
	    
	    @ManyToOne
	    @JoinColumn(name = "zone_id", nullable = false)
	    @Schema(description = "Zone to which customer belongs")
	    private Zone zone;
	    

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getRoles() {
			return roles;
		}

		public void setRoles(String roles) {
			this.roles = roles;
		}

		public Zone getZone() {
			return zone;
		}

		public void setZone(Zone zone) {
			this.zone = zone;
		}
	
    
	    
	    
    
}
