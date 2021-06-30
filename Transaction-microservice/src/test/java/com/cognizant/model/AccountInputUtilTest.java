package com.cognizant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.cognizant.util.AccountInput;

class AccountInputUtilTest {

	AccountInput accountInput = new AccountInput();

	@Test
	void setAccountIdTest() {
		accountInput.setAccountNumber(50002000);
		assertEquals(50002000, accountInput.getAccountNumber());
	}

	@Test
	void setAmountTest() {
		accountInput.setAmount(500);
		assertEquals(500, accountInput.getAmount());
	}

	@Test
	void getAccountIdTest() {
		accountInput.setAccountNumber(50001000);
		assertTrue(accountInput.getAccountNumber() == 50001000);
	}

	@Test
	void getAmountTest() {
		accountInput.setAmount(500);
		assertTrue(accountInput.getAmount() == 500);
	}

}
