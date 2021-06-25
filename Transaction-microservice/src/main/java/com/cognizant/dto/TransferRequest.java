package com.cognizant.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {

	private long sourceAccountNumber;
	private long targetAccountNumber;

	@Positive(message = "Transfer amount must be positive")
	@Min(value = 1, message = "Amount must be larger than 1")
	private double amount;
}
