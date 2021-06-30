package com.cognizant.models;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RulesInput {	
//Input needed for serviceCherges and minbalance Evaluation
	@NotNull
	private long accountNumber;
	@NotNull
	private double balance;
	@NotNull
	private double amount;

}
