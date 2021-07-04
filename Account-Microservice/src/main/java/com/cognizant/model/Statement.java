package com.cognizant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statement {
	private String date;
	private String Narration;
	private String referenceNo;
	private double amount;
	private double withdrawl;
	private double deposit;
	private double balance;

}
