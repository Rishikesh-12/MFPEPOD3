package com.cognizant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RulesInputTest {
	
	RulesInput rulesInput = new RulesInput();

	@Test
	void setAccountIdTest() {
		rulesInput.setAccountNumber(1);
		assertEquals(1, rulesInput.getAccountNumber());
	}

	@Test
	void setAmountTest() {
		rulesInput.setAmount(500);
		assertEquals(500, rulesInput.getAmount());
	}

}
