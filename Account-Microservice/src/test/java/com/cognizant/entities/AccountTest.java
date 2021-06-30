package com.cognizant.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AccountTest {

	Account account = new Account();

	@Test
	void setAccountNumberTest() {
		account.setAccountNumber(1);
		assertEquals(1, account.getAccountNumber());
	}

	@Test
	void setCustomerIdTest() {
		account.setCustomerId("Temp");
		assertEquals("Temp", account.getCustomerId());
	}

	@Test
	void setBalanceTest() {
		account.setBalance(6000);
		assertEquals(6000, account.getBalance());
	}

	@Test
	void setAccountTypeTest() {
		account.setAccountType("Savings");
		assertEquals("Savings", account.getAccountType());
	}

	@Test
	void setUsernameTest() {
		account.setUsername("Temp313");
		assertEquals("Temp313", account.getUsername());
	}

	@Test
	void setTransactionsTest() {
		account.setTransactions(null);
		assertEquals(null, account.getTransactions());
	}
}