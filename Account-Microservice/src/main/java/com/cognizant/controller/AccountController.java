package com.cognizant.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.dto.CreationStatus;
import com.cognizant.dto.Statement;
import com.cognizant.dto.TransactionStatus;
import com.cognizant.model.Account;
import com.cognizant.model.Transaction;
import com.cognizant.service.AccountService;

@RestController
@RequestMapping("/api")
public class AccountController {
	@Autowired
	private AccountService service;

	@PostMapping("account/{id}")
	public CreationStatus createAccount(@PathVariable("id") int id) {
		String accountId = service.createAccount(id, "Current");
		return new CreationStatus(accountId, "Account Created Successfully");
	}

	@GetMapping("accounts/{id}")
	public ResponseEntity<List<Account>> getCustomerAccounts(@PathVariable("id") int id) {
		List<Account> accList = service.getCustomerAccount(id);
		return new ResponseEntity<List<Account>>(accList, HttpStatus.FOUND);
	}

	@GetMapping("account/{id}")
	public ResponseEntity<Account> getAccount(@PathVariable("id") String id) {
		Optional<Account> acc = service.getAccount(id);
		if (acc.isEmpty())
			return new ResponseEntity<Account>(new Account(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Account>(acc.get(), HttpStatus.OK);
	}

	@GetMapping("account/{id}/{from_date}/{to_date}")
	public ResponseEntity<List<Statement>> getStatement(@PathVariable("id") long id,
			@PathVariable("from_date") String from_date, @PathVariable String to_date) {
		List<Statement> list = service.getStatement(id, from_date, to_date);
		return new ResponseEntity<List<Statement>>(list, HttpStatus.FOUND);
	}

	@PostMapping("withdrawal/{id}/{amount}/{reference}")
	public ResponseEntity<TransactionStatus> deposit(@PathVariable("id") String id, @PathVariable double amount,
			@PathVariable("reference") String reference) {
		return new ResponseEntity<TransactionStatus>(service.withdrawal(id, amount, reference), HttpStatus.OK);
	}
}
