package com.example.demo.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Schema(description = "Represents a customer's transaction")
public class CustomerTransaction 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Unique identifier for the transaction", example = "1")
    private Long id;

    @NotNull(message = "Customer ID is mandatory")
    @Schema(description = "ID of the customer associated with this transaction", example = "101")
    private Long customerId;

    @NotNull(message = "Amount is mandatory")
    @Min(value = 0, message = "Amount must be greater than or equal to 0")
    @Schema(description = "transaction amount in dollar", example = "150.75")
    private Double amount;

    @NotNull(message = "Date is mandatory")
    @Schema(description = "Date of the transaction in ISO format", example="2025-01-07")
    private LocalDate date;

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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "CustomerTransaction [id=" + id + ", customerId=" + customerId + ", amount=" + amount + ", date=" + date
				+ "]";
	}
    
    
    
}
