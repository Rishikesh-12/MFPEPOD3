package com.cognizant.service;

import java.util.List;

import com.cognizant.entities.Account;
import com.cognizant.model.AccountCreationStatus;
import com.cognizant.model.AccountInput;
import com.cognizant.model.AuthenticationResponse;

public interface AccountService {


	public List<Account> getCustomerAccount(String auth, String customerId);

	public Account getAccount(long accountNumber);

	public AccountCreationStatus createAccount(String auth,String customerId, Account account);

	public Account updateDepositBalance(AccountInput accountInput);

	public Account updateBalance(AccountInput accountInput);

	public Account updateAccount(String auth,Account account);

	public AuthenticationResponse hasPermission(String auth);

	public AuthenticationResponse hasAdminPermission(String auth);

	public AuthenticationResponse hasCustomerPermission(String auth);

}
