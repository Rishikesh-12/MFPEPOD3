package com.cognizant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.cognizant.entities.Transaction;
import com.cognizant.models.Account;

public class AccountTest {

	Transaction transaction = new Transaction();
	List<Transaction> transactions = new ArrayList<>();

	Account account = new Account();
	Account newAccount = new Account(1, "CUST101", 10000, "Savings", "USER1", transactions);

	@Test
	void setAccountNumberTest() {
		account.setAccountNumber(1);
		assertEquals(1, account.getAccountNumber());
	}

	@Test
	void setCustomerIdTest() {
		account.setCustomerId("CUST100");
		assertEquals("CUST100", account.getCustomerId());
	}

	@Test
	void setCurrentBalanceTest() {
		account.setBalance(10000);
		assertEquals(10000, account.getBalance());
	}

	@Test
	void setAccountTypeTest() {
		account.setAccountType("Savings");
		assertEquals("Savings", account.getAccountType());
	}

	@Test
	void setOwnerNameTest() {
		account.setUsername("USER1");
		assertEquals("USER1", account.getUsername());
	}

	@Test
	void setTransactionsTest() {
		account.setTransactions(null);
		assertEquals(null, account.getTransactions());
	}

	@Test
	void getAccountNumberTest() {
		account.setAccountNumber(1);
		assertTrue(account.getAccountNumber() == 1);
	}

	@Test
	void getCustomerIdTest() {
		account.setCustomerId("CUST100");
		assertTrue(account.getCustomerId() == "CUST100");
	}

	@Test
	void getAccountTypeTest() {
		account.setAccountType("Current");
		assertSame(account.getAccountType(), "Current");
	}

	@Test
	void getBalanceTest() {
		account.setBalance(10);
		assertTrue(account.getBalance() == 10);
	}

	@Test
	void getOwnerTest() {
		account.setUsername("user5");
		assertTrue(account.getUsername().equalsIgnoreCase("user5"));
	}

}
