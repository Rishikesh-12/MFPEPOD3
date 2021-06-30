package com.cognizant.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.exception.MinimumBalanceException;
import com.cognizant.feign.AccountFeign;
import com.cognizant.feign.RulesFeign;
import com.cognizant.models.Account;
import com.cognizant.models.RulesInput;
import com.cognizant.entities.Transaction;
import com.cognizant.repository.TransactionRepository;
import com.cognizant.util.AccountInput;
import com.cognizant.util.TransactionInput;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private AccountFeign accountFeign;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private RulesFeign ruleFeign;

	@Override
	public boolean makeTransfer(String auth, TransactionInput transactionInput) throws MinimumBalanceException {
		log.info("Transfering Amount");
		Account sourceAccount = null;
		Account targetAccount = null;
		if (transactionInput.getAmount() <= 0)
			return false;
		long sourceAccountNumber = transactionInput.getSourceAccount().getAccountNumber();
		sourceAccount = accountFeign.getAccount(auth, sourceAccountNumber);
		Boolean check = (Boolean) ruleFeign.evaluate(new RulesInput(sourceAccount.getAccountNumber(),
				sourceAccount.getBalance(), transactionInput.getAmount())).getBody();
		if (check.booleanValue() == false)
			throw new MinimumBalanceException("Minimum Balance 2000 should be maintaind");
		long targetAccountNumber = transactionInput.getTargetAccount().getAccountNumber();
		targetAccount = accountFeign.getAccount(auth, targetAccountNumber);

		if (sourceAccount != null && targetAccount != null) {
			if (isAmountAvailable(transactionInput.getAmount(), sourceAccount.getBalance())) {
				Transaction sourcetransaction = new Transaction();
				sourcetransaction.setAmount(transactionInput.getAmount());
				sourcetransaction.setSourceAccountNumber(sourceAccount.getAccountNumber());
				sourcetransaction.setSourceOwnerName(sourceAccount.getUsername());
				sourcetransaction.setTargetAccountNumber(targetAccount.getAccountNumber());
				sourcetransaction.setTargetOwnerName(targetAccount.getUsername());
				sourcetransaction.setDate(LocalDateTime.now());
				sourcetransaction.setReference("Transfer");
				transactionRepository.save(sourcetransaction);
				return true;
			}
		}
		return false;
	}

	private boolean isAmountAvailable(double amount, double balance) {
		return (balance - amount) > 0;
	}

	@Override
	public boolean makeWithdraw(String auth, AccountInput accountInput) {
		Account sourceAccount = null;

		long accountNumber = accountInput.getAccountNumber();
		sourceAccount = accountFeign.getAccount(auth, accountNumber);
		log.info("Withdrawing Amount");
		Boolean check = (Boolean) ruleFeign.evaluate(
				new RulesInput(accountInput.getAccountNumber(), sourceAccount.getBalance(), accountInput.getAmount()))
				.getBody();
		// String bodyToString = body.toString();
		if (check.booleanValue() == false)
			throw new MinimumBalanceException("Minimum Balance 2000 should be maintaind");

		if (sourceAccount != null) {
			Transaction transaction = new Transaction();
			transaction.setSourceAccountNumber(sourceAccount.getAccountNumber());
			transaction.setSourceOwnerName(sourceAccount.getUsername());
			transaction.setTargetAccountNumber(sourceAccount.getAccountNumber());
			transaction.setTargetOwnerName(sourceAccount.getUsername());
			transaction.setDate(LocalDateTime.now());
			transaction.setReference("Withdrawn");
			transaction.setAmount(accountInput.getAmount());
			transactionRepository.save(transaction);
			return true;
		}
		return false;
	}

	@Override
	public boolean makeServiceCharges(String auth, AccountInput accountInput) {
		log.info("method to make a service charges");
		Account sourceAccount = null;
		log.info("Deducting Service Charge");
		long accountNumber = accountInput.getAccountNumber();
		sourceAccount = accountFeign.getAccount(auth, accountNumber);
		if (sourceAccount != null) {
			Transaction transaction = new Transaction();
			transaction.setSourceAccountNumber(sourceAccount.getAccountNumber());
			transaction.setSourceOwnerName(sourceAccount.getUsername());
			transaction.setTargetAccountNumber(sourceAccount.getAccountNumber());
			transaction.setTargetOwnerName(sourceAccount.getUsername());
			transaction.setDate(LocalDateTime.now());
			transaction.setReference("Service Charge");
			transaction.setAmount(accountInput.getAmount());
			transactionRepository.save(transaction);
			return true;
		}

		return false;

	}

	@Override
	public boolean makeDeposit(String auth, AccountInput accountInput) {
		Account sourceAccount = null;
		log.info("Depositing Amount");
		long accountNumber = accountInput.getAccountNumber();
		sourceAccount = accountFeign.getAccount(auth, accountNumber);
		if (sourceAccount != null) {
			Transaction transaction = new Transaction();
			transaction.setSourceAccountNumber(sourceAccount.getAccountNumber());
			transaction.setSourceOwnerName(sourceAccount.getUsername());
			transaction.setTargetAccountNumber(sourceAccount.getAccountNumber());
			transaction.setTargetOwnerName(sourceAccount.getUsername());
			transaction.setDate(LocalDateTime.now());
			transaction.setReference("Deposit");
			transaction.setAmount(accountInput.getAmount());
			transactionRepository.save(transaction);
			return true;
		}
		return false;
	}
}
