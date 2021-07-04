package com.cognizant.service;

import com.cognizant.models.TransactionStatus;
import com.cognizant.util.AccountInput;
import com.cognizant.util.TransactionInput;

public interface TransactionService {

	public boolean makeTransfer(String token, TransactionInput transactionInput);

	public TransactionStatus makeWithdraw(String token, AccountInput accountInput);

	public boolean makeDeposit(String token, AccountInput accountInput);
	
}
