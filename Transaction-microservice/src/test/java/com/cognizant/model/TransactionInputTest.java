package com.cognizant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import com.cognizant.util.AccountInput;
import com.cognizant.util.TransactionInput;

class TransactionInputTest {

	TransactionInput transactionInput = new TransactionInput();
	AccountInput accountInput = new AccountInput(5000, 300);

	@Test
	void sourceAccountTest() {
		transactionInput.setSourceAccount(accountInput);
		assertEquals(5000, transactionInput.getSourceAccount().getAccountNumber());
	}

	@Test
	void targetAccountTest() {
		transactionInput.setTargetAccount(accountInput);
		assertNotEquals(400, transactionInput.getTargetAccount().getAmount());
	}

	@Test
	void amountTest() {
		transactionInput.setAmount(500);
		assertEquals(500, transactionInput.getAmount());
	}

	@Test
	void referenceTest() {
		transactionInput.setReference("Deposit");
		assertNotEquals("Withdraw", transactionInput.getReference());
	}

}
