package com.cognizant.feign;

import java.util.List;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cognizant.entities.Account;
import com.cognizant.model.AccountInput;
import com.cognizant.model.Transaction;
import com.cognizant.model.TransactionInput;
import com.cognizant.model.TransactionStatus;

@FeignClient(name = "transaction-ms", url = "${feign.url-transaction-ms}")
public interface TransactionFeign {

	@PostMapping("/deposit")
	public TransactionStatus makeDeposit(@RequestHeader("Authorization") String auth,
			@Valid @RequestBody AccountInput accountInput);

	@PostMapping("/withdraw")
	public TransactionStatus makeWithdraw(@RequestHeader("Authorization") String auth,
			@Valid @RequestBody AccountInput accountInput);

	@PostMapping(value = "/accounts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Account checkAccountBalance(@Valid @RequestBody AccountInput accountInput);

	@PostMapping(value = "/transfer")
	public TransactionStatus makeTransfer(@RequestHeader("Authorization") String auth,
			@Valid @RequestBody TransactionInput transactionInput);

	@GetMapping(value = "/getTransactionsByAccountNumber/{accountNumber}")
	public List<Transaction> getTransactionsByAccountNumber(@RequestHeader("Authorization") String auth,
			@PathVariable("accountNumber") long accountNumber);


}
