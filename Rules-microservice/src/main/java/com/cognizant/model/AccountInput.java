package com.cognizant.model;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountInput {
	
	/**
	 *  Class used for inputing account info during rules checking
	 */
	
	@NotNull(message = "Enter Account Number")
	private long accountNuber;
	@NotNull(message = "Enter Amount")
	private double amount;

}