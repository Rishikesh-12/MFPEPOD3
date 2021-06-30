package com.cognizant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.cognizant.models.AccountInput;

class AccountInputTest {

	AccountInput accountInput = new AccountInput();
	
//  Testing Setters
	@Test
	void setAccountNumberTest() {
		accountInput.setAccountNumber(11002000);
		assertEquals(11002000, accountInput.getAccountNumber());
	}

	@Test
	void setAmountTest() {
		accountInput.setAmount(10000);
		assertEquals(10000, accountInput.getAmount());
	}

	@Test
	void setBalanceTest() {
		accountInput.setBalance(1000);
		assertEquals(1000, accountInput.getBalance());
	}

	// Testing Getters
	@Test
	void getAccountNumberTest() {
		accountInput.setAccountNumber(1);
		assertTrue(1 == accountInput.getAccountNumber());
	}

	@Test
	void getAmountTest() {
		accountInput.setAmount(10000);
		assertTrue(10000 == accountInput.getAmount());
	}

	@Test
	void getCurrtentBalTest() {
		accountInput.setBalance(500);
		assertTrue(500 == accountInput.getBalance());
	}
}
