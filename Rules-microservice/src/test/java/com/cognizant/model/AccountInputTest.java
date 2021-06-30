package com.cognizant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


class AccountInputTest {

	AccountInput accInp = new AccountInput();
	AccountInput accInp2 = new AccountInput(1, 1000);

	@Test
	void setAccountNumberTest() {
		accInp.setAccountNuber(1);
		assertEquals(1, accInp.getAccountNuber());
	}

	@Test
	void setAmountTest() {
		accInp.setAmount(500);
		assertEquals(500, accInp.getAmount());
	}
}
