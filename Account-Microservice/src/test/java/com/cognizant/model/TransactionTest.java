package com.cognizant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.cognizant.model.Transaction;

class TransactionTest {

	Transaction transaction = new Transaction();
	Transaction transaction2 = new Transaction(1l, 1l, "Temp313", 3l, "Virat18", 1000, null, "deposit");

	@Test
	void setIdTest() {
		transaction.setId(1);
		assertEquals(1, transaction.getId());
	}

	@Test
	void setSourceAccountNumberTest() {
		transaction.setSourceAccountNumber(1);
		assertEquals(1, transaction.getSourceAccountNumber());
	}

	@Test
	void sourceOwnerNameTest() {
		transaction.setSourceOwnerName("Virat18");
		assertEquals("Virat18", transaction.getSourceOwnerName());
	}

	@Test
	void setTargetOwnerNameTest() {
		transaction.setTargetOwnerName("Temp313");
		assertEquals("Temp313", transaction.getTargetOwnerName());
	}

	@Test
	void setTargetAccountNumberTest() {
		transaction.setTargetAccountNumber(1);
		;
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