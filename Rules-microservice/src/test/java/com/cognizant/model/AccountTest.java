package com.cognizant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


class AccountTest {

	Account account = new Account();

	@Test
	void setAccountNumverTest() {
		account.setAccountNumber(1);
		assertEquals(1, account.getAccountNumber());
	}

	@Test
	void setCustomerIdTest() {
		account.setCustomerId("CUST101");
		assertEquals("CUST101", account.getCustomerId());
	}

	@Test
	void setCurrentBalanceTest() {
		account.setBalance(5000);
		assertEquals(5000, account.getBalance());
	}

	@Test
	void setAccountTypeTest() {
		account.setAccountType("Savings");
		assertEquals("Savings", account.getAccountType());
	}

	@Test
	void setOwnerNameTest() {
		account.setUsername("Joydeep");
		assertEquals("Joydeep", account.getUsername());
	}

	@Test
	void setTransactionsTest() {
		account.setTransactions(null);
		assertEquals(null, account.getTransactions());
	}
}
