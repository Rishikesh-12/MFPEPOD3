package com.cognizant.controller;

import java.util.Date;
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
import com.cognizant.feign.TransactionFeign;
import com.cognizant.model.AccountCreationStatus;
import com.cognizant.model.AccountInput;
import com.cognizant.model.Statement;
import com.cognizant.model.StatementRequest;
import com.cognizant.model.Transaction;
import com.cognizant.model.TransactionInput;
import com.cognizant.model.TransactionStatus;
import com.cognizant.repository.StatementRepository;
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

	@Autowired
	private StatementRepository statementRepository;

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
		token = auth;
		log.info("Inside Get Account Method");
		accountServiceImpl.hasPermission(auth);
		return new ResponseEntity<>(accountServiceImpl.getCustomerAccount(auth, customerId), HttpStatus.OK);
	}

	@PostMapping("/createAccount/{customerId}")
	public ResponseEntity<AccountCreationStatus> createAccount(@RequestHeader("Authorization") String auth,
			@PathVariable String customerId, @Valid @RequestBody Account account) {
		log.info("Inside Create Account Method");
		accountServiceImpl.hasAdminPermission(auth);
		AccountCreationStatus accountCreationStatus = accountServiceImpl.createAccount(auth, customerId, account);
		if (accountCreationStatus == null)
			return new ResponseEntity<>(accountCreationStatus, HttpStatus.NOT_ACCEPTABLE);
		log.info("Account Created Sucessfully");
		transactionFeign.makeDeposit(auth, new AccountInput(account.getAccountNumber(), account.getBalance()));
		accountServiceImpl.statement(account);
		return new ResponseEntity<>(accountCreationStatus, HttpStatus.CREATED);
	}

	@PostMapping("/deposit")
	public ResponseEntity<TransactionStatus> deposit(@RequestHeader("Authorization") String auth,
			@RequestBody AccountInput accountInput) {
		log.info("Inside Deposit Method");
		accountServiceImpl.hasPermission(auth);
		transactionFeign.makeDeposit(auth, accountInput);
		Account updateBalance = accountServiceImpl.updateDepositBalance(accountInput);
		TransactionStatus status = new TransactionStatus("Deposit was successfull", updateBalance.getBalance());
		List<Transaction> list = transactionFeign.getTransactionsByAccountNumber(auth, accountInput.getAccountNumber());
		updateBalance.setTransactions(list);
		log.info("Amount Deposited Successfully");
		accountServiceImpl.statement(accountInput, "deposit");
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	@PostMapping("/withdraw")
	public ResponseEntity<TransactionStatus> withdraw(@RequestHeader("Authorization") String auth,
			@RequestBody AccountInput accountInput) {
		log.info("Inside Withdraw Method");
		accountServiceImpl.hasPermission(auth);
		try {
			transactionFeign.makeWithdraw(auth, accountInput);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
		}
		Account updatedBalance = accountServiceImpl.updateBalance(accountInput);
		List<Transaction> transactions = transactionFeign.getTransactionsByAccountNumber(auth,
				accountInput.getAccountNumber());
		updatedBalance.setTransactions(transactions);
		log.info("Amount withdraw sucessful");
		accountServiceImpl.statement(accountInput, "withdraw");
		TransactionStatus transactionStatus = new TransactionStatus("Withdraw was successfull",
				updatedBalance.getBalance());
		return new ResponseEntity<>(transactionStatus, HttpStatus.OK);
	}

	@PostMapping("/transfer")
	public ResponseEntity<?> transfer(@RequestHeader("Authorization") String auth,
			@RequestBody TransactionInput transactionInput) {
		log.info("Inside Transfer Method");
		accountServiceImpl.hasPermission(auth);
		boolean status = true;
		try {
			transactionFeign.makeTransfer(auth, transactionInput);

		} catch (Exception e) {
			status = false;
			log.info("Transfer service declined");
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
		accountServiceImpl.statement(transactionInput, "transfer");
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

	@PostMapping("/getStatement")
	public ResponseEntity<List<Statement>> getStatement(@RequestHeader("Authorization") String auth,
			@RequestBody StatementRequest statementRequest) {
		long timeadj = 24 * 60 * 60 * 1000;
		statementRequest.setToDate(new Date(statementRequest.getToDate().getTime() + timeadj));
		List<Statement> list = statementRepository.findStatement(statementRequest.getAccountNo(),
				statementRequest.getFromDate(), statementRequest.getToDate());
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/find")
	public List<Account> getAllAccount() {
		log.info("Inside Find Account Method");
		List<Account> account = accountServiceImpl.getAllAccounts();
		log.info("Returned All Accounts");
		return account;
	}

	private String token;

	@PostMapping("/serviceCharge")
	public void serviceCharge(@RequestBody AccountInput accountInput) {
		log.info("Inside service charge Method");
		accountServiceImpl.hasPermission(token);
		try {
			transactionFeign.makeWithdraw(token, accountInput);

		} catch (Exception e) {
			log.info(e.getMessage());
		}
		Account updatedBalance = accountServiceImpl.updateBalance(accountInput);
		List<Transaction> transactions = transactionFeign.getTransactionsByAccountNumber(token,
				accountInput.getAccountNumber());
		updatedBalance.setTransactions(transactions);
		log.info("service charge deduction sucessful");
		accountServiceImpl.statement(accountInput, "Service Charge");
	}

}
