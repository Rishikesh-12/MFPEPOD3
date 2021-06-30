package com.cognizant.model;

import com.sun.istack.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

//Input needed for serviceCherges and minbalance Evaluation
public class RulesInput {	
	@NotNull
	private long accountNumber;
	@NotNull
	private double balance;
	@NotNull
	private double amount;

}
