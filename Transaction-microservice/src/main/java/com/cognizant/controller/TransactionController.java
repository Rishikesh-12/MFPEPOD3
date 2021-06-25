/**
 * 
 */
package com.cognizant.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.dto.DepositOrWithdrawRequest;
import com.cognizant.dto.TransferRequest;
import com.cognizant.service.TransactionService;
import com.cognizant.model.Transaction;

import lombok.AllArgsConstructor;

/**
 * @author Rishikesh
 *
 */

//POST: /deposit (Input: AccountId, amount) | Output (TransactionStatus ) 

@RestController
@RequestMapping("/bankapi/")
@AllArgsConstructor
public class TransactionController {

	private final TransactionService transactionService;

	@PostMapping("/deposit")
	public ResponseEntity<?> deposit(@RequestHeader("Authorization") String auth,
			@RequestBody DepositOrWithdrawRequest depositRequest) {
		boolean status = transactionService.deposit(auth, depositRequest);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	@PostMapping("/withdraw")
	public ResponseEntity<?> withdraw(@RequestHeader("Authorization") String auth,
			@RequestBody DepositOrWithdrawRequest withdrawRequest) {
		boolean status = transactionService.withdraw(auth, withdrawRequest);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	@PostMapping("/transfer")
	public ResponseEntity<?> transfer(@RequestHeader("Authorization") String auth,
			@Valid @RequestBody TransferRequest transferRequest) {
		boolean status = transactionService.transfer(auth, transferRequest);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	@GetMapping("/getTransactions/{accountNo}")
	public ResponseEntity<List<Transaction>> getTransactions(@RequestHeader("Authorization") String auth,
			@PathVariable long accountNo) {
		List<Transaction> transactionList = transactionService.getTransactions(auth, accountNo);
		return new ResponseEntity<>(transactionList, HttpStatus.OK);
	}

}
