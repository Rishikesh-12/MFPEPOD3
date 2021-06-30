package com.cognizant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TransactionInputTest {

	TransactionInput input = new TransactionInput();
	AccountInput Temp = new AccountInput(1, 2000);
	TransactionInput input2 = new TransactionInput(Temp, Temp, 1000, "Deposit");

	@Test
	void setSourceAccountTest() {
		input.setSourceAccount(Temp);
		assertEquals(2000, input.getSourceAccount().getAmount());
	}

	@Test
	void setTargetAccountTest() {
		input.setTargetAccount(Temp);
		assertEquals(1, input.getTargetAccount().getAccountNumber());
	}

	@Test
	void setAmountTest() {
		input.setAmount(1000);
		assertEquals(1000, input.getAmount());
	}

	@Test
	void setReferenceTest() {
		input.setReference("Withdraw");
		assertEquals("Withdraw", input.getReference());
	}
}