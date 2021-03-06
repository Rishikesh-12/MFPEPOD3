package com.cognizant.models;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountInput {
	@NotNull
	private long accountNumber;
	@NotNull
	private double balance;
	@NotNull
	private double amount;

}
