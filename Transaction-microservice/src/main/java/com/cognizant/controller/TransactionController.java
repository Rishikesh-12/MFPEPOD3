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
import com.cognizant.model.TransactionStatus;
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
	public TransactionStatus makeTransfer(@RequestHeader("Authorization") String token,
			@Valid @RequestBody TransactionInput transactionInput) {
		log.info("Initiating Transfer of ₹."+transactionInput.getAmount()+" from account no " + transactionInput.getSourceAccount().getAccountNumber()
				+ "to account no "+transactionInput.getTargetAccount().getAccountNumber());
		
		boolean status = transactionService.makeTransfer(token, transactionInput);
		
		if (status) {
			log.info("Transfer completed");
			return new TransactionStatus("Transfer Successfull",transactionInput.getAmount());
		} else {
			log.info("Transfer not completed");
		}
		return new TransactionStatus("Transfer Not Successfull",transactionInput.getAmount());
	}

	@GetMapping(value = "/getTransactionsByAccountNumber/{accountNumber}")
	public List<Transaction> getTransactionsByAccountNumber(@RequestHeader("Authorization") String token,
			@PathVariable("accountNumber") long accountNumber) {
		log.info("Getting Transaction History for Account Number " + accountNumber);
		List<Transaction> transactionHistory = transRepo
				.findBySourceAccountNumberOrTargetAccountNumberOrderByDate(accountNumber, accountNumber);
		return transactionHistory;
	}

	@PostMapping(value = "/withdraw")
	public TransactionStatus makeWithdraw(@RequestHeader("Authorization") String token,
			@Valid @RequestBody AccountInput accountInput) {
		log.info("Withdraw service initiated for account number" + accountInput.getAccountNumber());
		boolean status = transactionService.makeWithdraw(token, accountInput);
		log.info("Withdraw Successfull");
		if (status) return new TransactionStatus("Withdraw Successfull",accountInput.getAmount());
		return new TransactionStatus("Transfer Unsuccessfull",accountInput.getAmount());
	}

	@PostMapping(value = "/deposit")
	public TransactionStatus makeDeposit(@RequestHeader("Authorization") String token,
			@Valid @RequestBody AccountInput accountInput) {
		log.info("Deposit service initiated for account number " + accountInput.getAccountNumber());
		boolean status = transactionService.makeDeposit(token, accountInput);
		log.info("Deposit Successfull");
		if (status) return new TransactionStatus("Deposit Successfull",accountInput.getAmount());
		return new TransactionStatus("Deposit Unsuccessfull",accountInput.getAmount());
	}

}
