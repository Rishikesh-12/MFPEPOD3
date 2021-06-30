package com.cognizant.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.entities.Transaction;
import com.cognizant.repository.TransactionRepository;
import com.cognizant.service.TransactionServiceImpl;
import com.cognizant.util.AccountInput;
import com.cognizant.util.TransactionInput;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/bankapi")
public class TransactionController {

	@Autowired
	public TransactionRepository transRepo;

	@Autowired
	public TransactionServiceImpl transactionService;

	@PostMapping(value = "/transfer")
	public boolean makeTransfer(@RequestHeader("Authorization") String token,
			@Valid @RequestBody TransactionInput transactionInput) {
		log.info("Initiating Transfer");
		if (transactionInput != null) {
			boolean isComplete = transactionService.makeTransfer(token, transactionInput);

			return isComplete;
		} else {
			return false;
		}
	}

	@GetMapping(value = "/getTransactionsByAccountNumber/{accountNumber}")
	public List<Transaction> getTransactionsByAccountNumber(@RequestHeader("Authorization") String token,
			@PathVariable("accountNumber") long accountNumber) {
		log.info("Getting Transactions by Account Number");
		List<Transaction> transactions = transRepo
				.findBySourceAccountNumberOrTargetAccountNumberOrderByDate(accountNumber, accountNumber);
		return transactions;
	}

	@PostMapping(value = "/withdraw")
	public boolean makeWithdraw(@RequestHeader("Authorization") String token,
			@Valid @RequestBody AccountInput accountInput) {
		log.info("Withdraw Initiated");
		transactionService.makeWithdraw(token, accountInput);
		return true;
	}

	@PostMapping(value = "/servicecharge")
	public boolean makeServiceCharges(@RequestHeader("Authorization") String token,
			@Valid @RequestBody AccountInput accountInput) {
		log.info("Deducting Service Charge");
		transactionService.makeServiceCharges(token, accountInput);
		return true;
	}

	@PostMapping(value = "/deposit")
	public ResponseEntity<?> makeDeposit(@RequestHeader("Authorization") String token,
			@Valid @RequestBody AccountInput accountInput) {
		log.info("Deposit Initiated");
		transactionService.makeDeposit(token, accountInput);
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

}
