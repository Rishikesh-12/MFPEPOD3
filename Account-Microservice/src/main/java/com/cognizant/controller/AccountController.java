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
import com.cognizant.model.Statement;
import com.cognizant.model.Transaction;
import com.cognizant.model.TransactionInput;
import com.cognizant.model.TransactionStatus;
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

	@PostMapping("/createAccount/{customerId}")
	public ResponseEntity<AccountCreationStatus> createAccount(@RequestHeader("Authorization") String auth, @PathVariable String customerId,
			@Valid @RequestBody Account account) {
		log.info("Inside Create Account Method");
		accountServiceImpl.hasAdminPermission(auth);
		AccountCreationStatus accountCreationStatus = accountServiceImpl.createAccount(auth, customerId, account);
		if (accountCreationStatus == null)
			return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
		log.info("Account Created Sucessfully");

		transactionFeign.makeDeposit(auth, new AccountInput(account.getAccountNumber(), account.getBalance()));
		return new ResponseEntity<>(accountCreationStatus, HttpStatus.CREATED);
	}

	@PostMapping("/deposit")
	public ResponseEntity<TransactionStatus> deposit(@RequestHeader("Authorization") String auth,
			@RequestBody AccountInput accountInput) {
		log.info("Inside Deposit Method");
		accountServiceImpl.hasPermission(auth);
		transactionFeign.makeDeposit(auth, accountInput);
		Account updateBalance = accountServiceImpl.updateDepositBalance(accountInput);
		TransactionStatus status=new TransactionStatus("Deposit was successfull", updateBalance.getBalance());
		List<Transaction> list = transactionFeign.getTransactionsByAccountNumber(auth, accountInput.getAccountNumber());
		updateBalance.setTransactions(list);
		log.info("Amount Deposited Successfully");
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	@PostMapping("/withdraw")
	public TransactionStatus withdraw(@RequestHeader("Authorization") String auth,
			@RequestBody AccountInput accountInput) {
		log.info("Inside Withdraw Method");
		accountServiceImpl.hasPermission(auth);
		/*boolean status = true;
		try {
			transactionFeign.makeWithdraw(auth, accountInput);

		} catch (Exception e) {
			status = false;
			return new ResponseEntity<>(status, HttpStatus.NOT_ACCEPTABLE);
		}*/
		Account updatedBalance = accountServiceImpl.updateBalance(accountInput);
		TransactionStatus status=new TransactionStatus("Withdraw was successfull", updatedBalance.getBalance());
		List<Transaction> transactions = transactionFeign.getTransactionsByAccountNumber(auth,
				accountInput.getAccountNumber());
		updatedBalance.setTransactions(transactions);
		log.info("Amount withdraw sucessful");
		return status;
	}

	@PostMapping("/transfer")
	public ResponseEntity<?> transfer(@RequestHeader("Authorization") String auth,
			@RequestBody TransactionInput transactionInput) {
		log.info("Inside Transfer Method");
		accountServiceImpl.hasPermission(auth);
		boolean status = true;
		try {
			status = transactionFeign.makeTransfer(auth, transactionInput);

		} catch (Exception e) {
			return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
		}
		if (status == false) {
			return new ResponseEntity<>(false, HttpStatus.NOT_IMPLEMENTED);
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
		return new ResponseEntity<>(true, HttpStatus.OK);
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
	
	@GetMapping("/getStatement/{accountNumber}/{from_date}/{to_date}")
	public ResponseEntity<List<Statement>> getSatement(@RequestHeader("Authorization") String auth,@PathVariable("accountNumber") long id,
			@PathVariable("from_date") String from_date, @PathVariable String to_date){
		System.out.println(from_date + "  asd   " + to_date);
		accountServiceImpl.hasAdminPermission(auth);
		List<Statement> list = accountServiceImpl.getStatement(id, from_date, to_date);
		return new ResponseEntity<List<Statement>>(list, HttpStatus.OK);
	}
}
