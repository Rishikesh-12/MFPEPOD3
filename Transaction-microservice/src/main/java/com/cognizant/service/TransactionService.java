package com.cognizant.service;

import java.util.List;

import com.cognizant.dto.DepositOrWithdrawRequest;
import com.cognizant.dto.TransferRequest;
import com.cognizant.model.Transaction;

public interface TransactionService {

	public boolean transfer(String auth, TransferRequest transferRequest);

	public boolean withdraw(String auth, DepositOrWithdrawRequest withdrawRequest);

	public boolean deposit(String auth, DepositOrWithdrawRequest depositRequest);

	public List<Transaction> getTransactions(String auth, long accountno);
//	public boolean deductServiceCharges(String auth, WDAccountInput accountInput);

}