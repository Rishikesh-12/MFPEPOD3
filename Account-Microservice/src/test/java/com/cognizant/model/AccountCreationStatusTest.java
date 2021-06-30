package com.cognizant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.cognizant.model.AccountCreationStatus;

class AccountCreationStatusTest {

	AccountCreationStatus status = new AccountCreationStatus();

	@Test
	void setAccountNumberTest() {
		status.setAccountNumber(3);
		assertEquals(3, status.getAccountNumber());
	}

	@Test
	void setMessageTest() {
		status.setMessage("Created");
		assertEquals("Created", status.getMessage());
	}
}