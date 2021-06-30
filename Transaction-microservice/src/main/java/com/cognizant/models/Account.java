package com.cognizant.models;

import java.util.List;
import javax.persistence.Transient;

import com.cognizant.entities.Transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

	private long accountNumber;

	private String customerId;

	private double balance;

	private String accountType;

	private String username;

	@Transient
	private transient List<Transaction> transactions;

}