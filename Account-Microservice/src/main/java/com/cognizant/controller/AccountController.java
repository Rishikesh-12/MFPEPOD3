package com.cognizant.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.entities.Account;
import com.cognizant.exceptions.MinimumBalanceException;
import com.cognizant.feign.TransactionFeign;
import com.cognizant.model.AccountCreationStatus;
import com.cognizant.model.AccountInput;
import com.cognizant.model.Transaction;
import com.cognizant.model.TransactionInput;
import com.cognizant.service.AccountServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/bankapi")
public class AccountController {

	@Autowired
	private AccountServiceImpl accountServiceImpl;

	@Autowired
	private TransactionFeign transactionFeign;

	@GetMapping("/getAccount/{accountNumber}")
	public ResponseEntity<Account> getAccount(@RequestHeader("Authorization") String auth,
			@PathVariable long accountNumber) {
		log.info("Inside Get Account Method");
		accountServiceImpl.hasPermission(auth);
		Account account = accountServiceImpl.getAccount(accountNumber);
		log.info("Account Details Returned Sucessfully");
		return new ResponseEntity<>(account, HttpStatus.OK);
	}
	
	@GetMapping("/getAccounts/{customerId}")
	public ResponseEntity<List<Account>> getCustomerAccount(@RequestHeader("Authorization") String auth,
			@PathVariable String customerId) {
		log.info("Inside Get Account Method");
		accountServiceImpl.hasPermission(auth);
		return new ResponseEntity<>(accountServiceImpl.getCustomerAccount(auth, customerId), HttpStatus.OK);
	}
	
	@GetMapping("/checkBalance/{accountNumber}")
	public ResponseEntity<?> checkAccountBalance(@RequestHeader("Authorization") String auth,
			@PathVariable long accountNumber) {
		log.info("Inside Check Account Balance Method");
		accountServiceImpl.hasPermission(auth);
		double Balance = accountServiceImpl.getAccount(accountNumber).getBalance();
		log.info("Balance Retrieved successfully");
		return new ResponseEntity<>(Balance, HttpStatus.OK);
	}

	@GetMapping("/find")
	public ResponseEntity<List<Account>> getAllAccount(@RequestHeader("Authorization") String auth) {
		log.info("Inside Find Account Method");
		accountServiceImpl.hasPermission(auth);
		List<Account> account = accountServiceImpl.getAllAccounts();
		log.info("Returned All Accounts");
		return new ResponseEntity<>(account, HttpStatus.OK);
	}

	@PostMapping("/createAccount/{customerId}")
	public ResponseEntity<?> createAccount(@RequestHeader("Authorization") String auth, @PathVariable String customerId,
			@Valid @RequestBody Account account) {
		log.info("Inside Create Account Method");
		accountServiceImpl.hasAdminPermission(auth);
		AccountCreationStatus accountCreationStatus = accountServiceImpl.createAccount(auth, customerId, account);
		if (accountCreationStatus == null)
			return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
		log.info("Account Created Sucessfully");
		
		transactionFeign.makeDeposit(auth, new AccountInput(account.getAccountNumber(),account.getBalance()));
		return new ResponseEntity<>(true, HttpStatus.CREATED);
	}
	
	@PostMapping("/deposit")
	public ResponseEntity<Account> deposit(@RequestHeader("Authorization") String auth,
			@RequestBody AccountInput accountInput) {
		log.info("Inside Deposit Method");
		accountServiceImpl.hasPermission(auth);
		transactionFeign.makeDeposit(auth, accountInput);
		Account updateBalance = accountServiceImpl.updateDepositBalance(accountInput);
		List<Transaction> list = transactionFeign.getTransactionsByAccountNumber(auth, accountInput.getAccountNumber());
		updateBalance.setTransactions(list);
		log.info("Amount Deposited Successfully");
		return new ResponseEntity<>(updateBalance, HttpStatus.OK);
	}

	@PostMapping("/withdraw")
	public ResponseEntity<Account> withdraw(@RequestHeader("Authorization") String auth,
			@RequestBody AccountInput accountInput) {
		log.info("Inside Withdraw Method");
		accountServiceImpl.hasPermission(auth);
		try {
			transactionFeign.makeWithdraw(auth, accountInput);

		} catch (Exception e) {
			throw new MinimumBalanceException("Minimum Balance 1000 should be maintaind");
		}
		Account updatedBalance = accountServiceImpl.updateBalance(accountInput);
		List<Transaction> transactions = transactionFeign.getTransactionsByAccountNumber(auth,
				accountInput.getAccountNumber());
		updatedBalance.setTransactions(transactions);
		log.info("Amount withdraw sucessful");
		return new ResponseEntity<>(updatedBalance, HttpStatus.OK);
	}

	@PostMapping("/servicecharge")
	public ResponseEntity<Account> servicecharge(@RequestHeader("Authorization") String auth,
			@RequestBody AccountInput accountInput) {
		log.info("Inside Service Charge Method");
		accountServiceImpl.hasPermission(auth);
		try {
			transactionFeign.makeServiceCharges(auth, accountInput);

		} catch (Exception e) {
			throw new MinimumBalanceException("Minimum Balance 1000 should be maintaind");
		}
		Account updatedBalance = accountServiceImpl.updateBalance(accountInput);
		List<Transaction> transaction = transactionFeign.getTransactionsByAccountNumber(auth,
				accountInput.getAccountNumber());
		updatedBalance.setTransactions(transaction);
		log.info("Service Charges Deducted Successfully");
		return new ResponseEntity<>(updatedBalance, HttpStatus.OK);
	}

	@PostMapping("/transfer")
	public ResponseEntity<String> transfer(@RequestHeader("Authorization") String auth,
			@RequestBody TransactionInput transactionInput) {
		log.info("Inside Transfer Method");
		accountServiceImpl.hasPermission(auth);
		boolean status = true;
		try {
			status = transactionFeign.makeTransfer(auth, transactionInput);

		} catch (Exception e) {
			throw new MinimumBalanceException("Minimum Balance 1000 should be maintained");
		}
		if (status == false) {
			return new ResponseEntity<>("Transaction Failed", HttpStatus.NOT_IMPLEMENTED);
		}
		Account updatedSourceBalance = accountServiceImpl.updateBalance(transactionInput.getSourceAccount());
		List<Transaction> sourcelist = transactionFeign.getTransactionsByAccountNumber(auth,
				transactionInput.getSourceAccount().getAccountNumber());
		updatedSourceBalance.setTransactions(sourcelist);

		Account updatedTargetBalance = accountServiceImpl.updateDepositBalance(transactionInput.getTargetAccount());
		List<Transaction> targetlist = transactionFeign.getTransactionsByAccountNumber(auth,
				transactionInput.getTargetAccount().getAccountNumber());
		updatedTargetBalance.setTransactions(targetlist);
		log.info("Transfer Completed Successfully");
		return new ResponseEntity<>("Transaction Made Successfully From Source Account Number"
				+ transactionInput.getSourceAccount().getAccountNumber() + " To Account Number "
				+ transactionInput.getTargetAccount().getAccountNumber() + " ", HttpStatus.OK);
	}
	
	@PutMapping("/updateAccount")
	public ResponseEntity<Account> updateAccount(@RequestHeader("Authorization") String auth,
			@RequestBody Account account) {
		log.info("Inside Update Account Method");
		accountServiceImpl.hasPermission(auth);
		Account updatedAccount = accountServiceImpl.updateAccount(auth, account);
		log.info("Account Details Updated Sucessfully");
		return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
	}

}
