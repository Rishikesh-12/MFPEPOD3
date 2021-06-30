package com.cognizant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TransactionTest {

	Transaction transaction = new Transaction();

	@Test
	void setUserIdTest() {
		transaction.setId(1);
		assertEquals(1, transaction.getId());
	}

	@Test
	void setSourceAccountIdTest() {
		transaction.setSourceAccountNumber(1);
		assertEquals(1, transaction.getSourceAccountNumber());
	}

	@Test
	void sourceOwnerNameTest() {
		transaction.setSourceOwnerName("CUSTOMER");
		assertEquals("CUSTOMER", transaction.getSourceOwnerName());
	}

	@Test
	void setTargetOwnerNameTest() {
		transaction.setTargetOwnerName("CUSTOMER");
		assertEquals("CUSTOMER", transaction.getTargetOwnerName());
	}

	@Test
	void setTargetAccountNumberTest() {
		transaction.setTargetAccountNumber(1);
		assertEquals(1, transaction.getTargetAccountNumber());
	}

	@Test
	void setAmountTest() {
		transaction.setAmount(1000);
		assertEquals(1000, transaction.getAmount());
	}

	@Test
	void setReferenceTest() {
		transaction.setReference("Deposit");
		assertEquals("Deposit", transaction.getReference());
	}

	@Test
	void setDateTest() {
		transaction.setDate(null);
		assertEquals(null, transaction.getDate());
	}
}