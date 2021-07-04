package com.cognizant.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class TransactionTest {

	Transaction transaction = new Transaction();

	@Test
	void idTest() {
		transaction.setId(1);
		assertEquals(1, transaction.getId());
	}

	@Test
	void sourceAccountNumberTest() {
		transaction.setSourceAccountNumber(10002000);
		assertEquals(10002000, transaction.getSourceAccountNumber());
	}

	@Test
	void sourceOwnerNameTest() {
		transaction.setSourceOwnerName("Geek");
		assertEquals("Geek", transaction.getSourceOwnerName());
	}

	@Test
	void targetAccountNumberTest() {
		transaction.setTargetAccountNumber(20005000);
		assertNotEquals(50002000, transaction.getTargetAccountNumber());
	}
	
	@Test
	void targetOwnerNameTest() {
		transaction.setTargetOwnerName("Alice");
		assertEquals("Alice", transaction.getTargetOwnerName());
	}

	@Test
	void amountTest() {
		transaction.setAmount(1000);
		assertEquals(1000, transaction.getAmount());
	}

	@Test
	void referenceTest() {
		transaction.setReference("Savings");
		assertEquals("Savings", transaction.getReference());
	}

	//@Test
	/*void dateTest() {
		transaction.setDate(LocalDateTime.of(2021, 6, 19, 12, 55));
		assertNotEquals(LocalDateTime.of(2021, 4, 20, 1, 40), transaction.getDate());
	}*/
}
