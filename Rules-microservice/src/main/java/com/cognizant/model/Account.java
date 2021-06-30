package com.cognizant.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	/**
	 * Account Entity used in Repository
	 */
	@Id
	@NotNull(message = "Enter Account Number")
	private long accountNumber;

	@NotNull(message = "Enter customerId")
	private String customerId;

	@NotNull(message = "Enter currentBalance")
	private double balance;

	@NotNull(message = "Enter accountType")
	private String accountType;

	@Column(length = 20)
	@NotNull(message = "Enter ownerName")
	private String username;

	@Transient
	private List<Transaction> transactions;

}