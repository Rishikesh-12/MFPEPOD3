/**
 * 
 */
package com.cognizant.service;

import java.util.Date;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.dto.DepositOrWithdrawRequest;
import com.cognizant.dto.RulesRequest;
import com.cognizant.dto.TransferRequest;
import com.cognizant.exception.MinimumBalanceException;
import com.cognizant.feign.AccountFeign;
import com.cognizant.feign.RuleFeign;
import com.cognizant.model.Account;
import com.cognizant.model.Transaction;
import com.cognizant.repository.TransactionRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Rishikesh
 *
 */

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private AccountFeign accountFeign;
	
	@Autowired
	private RuleFeign ruleFeign;

	@Override
	public boolean withdraw(String auth, DepositOrWithdrawRequest withdrawRequest) {
		log.info("withdraw service invoked");
		long sourceAccountNumber = withdrawRequest.getAccountNumber();
		double amount = withdrawRequest.getAmount();
		Account sourceAccount = accountFeign.getAccount(auth, sourceAccountNumber);
		Boolean ruleStatus = (Boolean) ruleFeign.evaluate(new RulesRequest(sourceAccountNumber,
				sourceAccount.getCurrentBalance(), amount)).getBody();

		if (ruleStatus == false)
			throw new MinimumBalanceException("Minimum Balance 1000 should be maintaind");

		if (sourceAccount != null) {
			sourceAccount.setCurrentBalance(sourceAccount.getCurrentBalance() - amount);
			Transaction transaction = new Transaction();
			transaction.setAmount(amount);
			transaction.setSourceAccountNumber(sourceAccountNumber);
			transaction.setTargetAccountNumber(sourceAccountNumber);
			transaction.setSourceOwnerName(sourceAccount.getOwnerName());
			transaction.setTargetOwnerName(sourceAccount.getOwnerName());
			transaction.setTransactionDate(new Date());
			transaction.setReference("Withdraw");
			transactionRepository.save(transaction);
			accountFeign.updateAccount(sourceAccount);
			return true;
		}
		return false;

	}

	@Override
	public boolean deposit(String auth, DepositOrWithdrawRequest depositRequest) {
		log.info("deposit service invoked");
		long sourceAccountNumber = depositRequest.getAccountNumber();
		double amount = depositRequest.getAmount();
		Account sourceAccount = accountFeign.getAccount(auth, sourceAccountNumber);
		if(sourceAccount != null) {
			sourceAccount.setCurrentBalance(sourceAccount.getCurrentBalance()+ amount);
			Transaction transaction = new Transaction();
			transaction.setAmount(amount);
			transaction.setSourceAccountNumber(sourceAccountNumber);
			transaction.setTargetAccountNumber(sourceAccountNumber);
			transaction.setSourceOwnerName(sourceAccount.getOwnerName());
			transaction.setTargetOwnerName(sourceAccount.getOwnerName());
			transaction.setTransactionDate(new Date());
			transaction.setReference("Deposit");
			transactionRepository.save(transaction);
			accountFeign.updateAccount(sourceAccount);
			return true;
		}
		return false;
	}

	@Override
	public boolean transfer(String auth, TransferRequest transferRequest) {
		log.info("transfer service invoked");
		long sourceAccountNumber = transferRequest.getSourceAccountNumber();
		long targetAccountNumber = transferRequest.getTargetAccountNumber();
		double amount = transferRequest.getAmount();
		Account sourceAccount = accountFeign.getAccount(auth, sourceAccountNumber);
		Account targetAccount = accountFeign.getAccount(auth, targetAccountNumber);
		if (sourceAccount != null && targetAccount != null && sourceAccount.getCurrentBalance() - amount >= 0) {
			targetAccount.setCurrentBalance(targetAccount.getCurrentBalance() + amount);
			sourceAccount.setCurrentBalance(sourceAccount.getCurrentBalance() - amount);
			accountFeign.updateAccount(sourceAccount);
			accountFeign.updateAccount(targetAccount);
			
			Transaction transaction = new Transaction();
			transaction.setAmount(amount);
			transaction.setSourceAccountNumber(sourceAccountNumber);
			transaction.setTargetAccountNumber(targetAccountNumber);
			transaction.setSourceOwnerName(sourceAccount.getOwnerName());
			transaction.setTargetOwnerName(targetAccount.getOwnerName());
			transaction.setTransactionDate(new Date());
			transaction.setReference("Transfer");
			transactionRepository.save(transaction);
			return true;
		}
		return false;
	}

	@Override
	public List<Transaction> getTransactions(String auth, long accountNumber) {
		log.info("getTransactions service invoked");
		List<Transaction> transactionList = transactionRepository
				.findBySourceAccountNumberOrTargetAccountNumberOrderByTransactionDate(accountNumber, accountNumber);
		return transactionList;
	}

}
