package com.cognizant.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.entities.Account;
import com.cognizant.exceptions.AccessDeniedException;
import com.cognizant.exceptions.AccountNotFoundException;
import com.cognizant.feign.AuthFeign;
import com.cognizant.feign.TransactionFeign;
import com.cognizant.model.AccountCreationStatus;
import com.cognizant.model.AccountInput;
import com.cognizant.model.AuthenticationResponse;
import com.cognizant.model.Statement;
import com.cognizant.model.TransactionInput;
import com.cognizant.repository.AccountRepository;
import com.cognizant.repository.StatementRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private StatementRepository statementRepository;

	@Autowired
	private AuthFeign authFeignClient;

	@Autowired
	private TransactionFeign transactionFeign;
	
	@Override
	public AccountCreationStatus createAccount(String auth, String customerId, Account account) {
		Account newAccount = accountRepository.findByAccountNumber(account.getAccountNumber());
		if (newAccount != null) {
			AccountCreationStatus accountCreationStatus = new AccountCreationStatus();
			accountCreationStatus = null;
			log.info("Account already Exists");
			return accountCreationStatus;
		} else {
			AccountCreationStatus accountCreationStatus = null;
			if(account.getBalance()>=2000) { 
				accountRepository.save(account);
				accountCreationStatus = new AccountCreationStatus(account.getAccountNumber(),
						"Account Created Sucessfully");
				log.info("Account Created Sucessfully");
			}
			return accountCreationStatus;
		}
	}

	@Override
	public List<Account> getCustomerAccount(String token, String customerId) {
		List<Account> listAccounts = accountRepository.findByCustomerId(customerId);
		for (Account account : listAccounts) {
			account.setTransactions(transactionFeign.getTransactionsByAccountNumber(token, account.getAccountNumber()));
		}
		return listAccounts;
	}

	@Override
	public Account getAccount(long accountNumber) {
		Account account = accountRepository.findByAccountNumber(accountNumber);
		if (account == null) {
			throw new AccountNotFoundException("Account Not Found");
		}
		return account;
	}

	@Override
	public Account updateBalance(AccountInput accountInput) {
		Account account = accountRepository.findByAccountNumber(accountInput.getAccountNumber());
		account.setBalance(account.getBalance() - accountInput.getAmount());
		return accountRepository.save(account);
	}

	@Override
	public Account updateDepositBalance(AccountInput accountInput) {
		Account updatedBalance = accountRepository.findByAccountNumber(accountInput.getAccountNumber());
		updatedBalance.setBalance(updatedBalance.getBalance() + accountInput.getAmount());
		return accountRepository.save(updatedBalance);
	}

	@Override
	public Account updateAccount(String auth,Account account) {
		Account updatedAccount = accountRepository.findByAccountNumber(account.getAccountNumber());
		updatedAccount.setAccountType(account.getAccountType());
		updatedAccount.setBalance(account.getBalance());
		updatedAccount.setUsername(account.getUsername());
		updatedAccount.setCustomerId(account.getCustomerId());
		accountRepository.save(updatedAccount);
		return updatedAccount;
	}
	
	

	@Override
	public AuthenticationResponse hasPermission(String token) {
		return authFeignClient.getValidity("Bearer " + token);
	}

	@Override
	public AuthenticationResponse hasAdminPermission(String token) {
		AuthenticationResponse validity = authFeignClient.getValidity("Bearer " + token);
		if (!authFeignClient.getRole(validity.getUserId()).equals("ADMIN"))
			throw new AccessDeniedException("Access Not Granted");
		else
			return validity;
	}

	@Override
	public AuthenticationResponse hasCustomerPermission(String token) {
		AuthenticationResponse validity = authFeignClient.getValidity("Bearer " + token);
		if (!authFeignClient.getRole(validity.getUserId()).equals("CUSTOMER"))
			throw new AccessDeniedException("Access Not Granted");
		else
			return validity;
	}

	public void statement(Account account) {
		Statement st = new Statement();
		if(account!=null) {
			st.setAccountNo(account.getAccountNumber());
			st.setClosingBalance(2000);
			st.setDeposits(2000);
			st.setWithdraws(0);
			st.setNarration("Account Creation");
			st.setRefNo(0);
			Date d = new Date();
			st.setDate(d);
		}
		statementRepository.save(st);
	}
	
	public void statement(AccountInput accountInput,String transactionType) {
		Statement st = new Statement();
		Account account = getAccount(accountInput.getAccountNumber());
		if(account!=null) {
			st.setAccountNo(accountInput.getAccountNumber());
			st.setClosingBalance(account.getBalance());
			if (transactionType.equalsIgnoreCase("Deposit")) {
				st.setDeposits(accountInput.getAmount());
				st.setWithdraws(0);
				st.setNarration("Deposit to Account");
			}else {
				st.setDeposits(0);
				st.setWithdraws(accountInput.getAmount());
				st.setNarration("Withdrawl from Account");
			}
			st.setRefNo(0);
			st.setDate(new Date());
		}
		statementRepository.save(st);
	}
	
	public void statement(TransactionInput transactionInput, String transactionType) {
		Statement sst = new Statement();
		Statement tst = new Statement();
		Account saccount = getAccount(transactionInput.getSourceAccount().getAccountNumber());
		Account taccount = getAccount(transactionInput.getTargetAccount().getAccountNumber());
		if(saccount!=null) {
			sst.setAccountNo(saccount.getAccountNumber());
			sst.setClosingBalance(saccount.getBalance());
			if (saccount != taccount) {
				sst.setDeposits(0);
				sst.setWithdraws(transactionInput.getAmount());
				sst.setNarration("Transfer to account number"+ transactionInput.getTargetAccount().getAccountNumber());
			}
			else {
				sst.setDeposits(transactionInput.getAmount());
				sst.setWithdraws(transactionInput.getAmount());
				sst.setNarration("Self Transfer");
			}
			sst.setRefNo(0);
			sst.setDate(new Date());
		}
		statementRepository.save(sst);
		
		if(saccount != taccount) {
			tst.setAccountNo(taccount.getAccountNumber());
			tst.setClosingBalance(taccount.getBalance());
			tst.setDeposits(transactionInput.getAmount());
			tst.setWithdraws(0);
			tst.setNarration("Transferd from account number"+ transactionInput.getSourceAccount().getAccountNumber());
			tst.setRefNo(0);
			tst.setDate(new Date());
		}
		statementRepository.save(tst);
	}
	
	

}