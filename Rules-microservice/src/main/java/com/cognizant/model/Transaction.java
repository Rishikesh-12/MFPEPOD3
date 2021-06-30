package com.cognizant.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
	
	/**
	 *  Class used for inputing transaction entity
	 */
	private long id;
	private long sourceAccountNumber;
	private String sourceOwnerName;
	private long targetAccountNumber;
	private String targetOwnerName;
	private double amount;
	private LocalDateTime date;
	private String reference;

}