package com.cognizant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.cognizant.models.RulesInput;

class RulesInputTest {

	RulesInput rulesInput = new RulesInput();

	@Test
	void setAccountNumberTest() {
		rulesInput.setAccountNumber(50001000);
		assertEquals(50001000, rulesInput.getAccountNumber());
	}

	@Test
	void setAmountTest() {
		rulesInput.setAmount(5000);
		assertEquals(5000, rulesInput.getAmount());
	}

	@Test
	void setBalanceTest() {
		rulesInput.setBalance(20000);
		assertEquals(20000, rulesInput.getBalance());
	}

	@Test
	void getAccountIdTest() {
		rulesInput.setAccountNumber(50001000);
		assertTrue(rulesInput.getAccountNumber() == 50001000);
	}

	@Test
	void getCurrBalanceTest() {
		rulesInput.setBalance(20000);
		assertTrue(rulesInput.getBalance() == 20000);
	}

	@Test
	void getAmountTest() {
		rulesInput.setAmount(5000);
		assertTrue(rulesInput.getAmount() == 5000);
	}

}
