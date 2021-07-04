package com.cognizant.service;

import com.cognizant.util.AccountInput;
import com.cognizant.util.TransactionInput;

public interface TransactionService {

	public boolean makeTransfer(String token, TransactionInput transactionInput);

	public boolean makeWithdraw(String token, AccountInput accountInput);

	public boolean makeDeposit(String token, AccountInput accountInput);
	
}
