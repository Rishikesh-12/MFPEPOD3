package com.cognizant.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

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
	private List<Transaction> transactions = new ArrayList<>();

}