package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@Entity
@Schema(description = "Respresent the reward points earned by a customer in a specific month and year.")
public class RewardPoints 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Unique identifier for the reward entry", example = "1")
    private Long id;

    @NotNull(message = "Customer ID is mandatory")
    @Schema(description = "Unique identifier for the customer", example = "101")
    private Long customerId;

    @NotNull(message = "Month is mandatory")
    @Schema(description = "Month in which the reward points were earned(1-12",example = "5")
    @Min(value = 1, message = "Month must be between 1 and 12")
    private Integer month;

    @NotNull(message = "Year is mandatory")
    @Schema(description = "Year in which the reward points were earned",example = "2025")
    private Integer year;

    @NotNull(message = "Points are mandatory")
    @Min(value = 0, message = "Points must be greater than or equal to 0")
    @Schema(description = "Reward points earned by the customer", example="150")
    private Integer points;

	

	

	public RewardPoints() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RewardPoints(Long id, @NotNull(message = "Customer ID is mandatory") Long customerId,
			@NotNull(message = "Month is mandatory") @Min(value = 1, message = "Month must be between 1 and 12") Integer month,
			@NotNull(message = "Year is mandatory") Integer year,
			@NotNull(message = "Points are mandatory") @Min(value = 0, message = "Points must be greater than or equal to 0") Integer points) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.month = month;
		this.year = year;
		this.points = points;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return "RewardPoints [id=" + id + ", customerId=" + customerId + ", month=" + month + ", year=" + year
				+ ", points=" + points + "]";
	}
    
    
    
    
    
}
