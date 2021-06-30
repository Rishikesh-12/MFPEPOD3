package com.cognizant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.cognizant.models.ErrorDetails;

class ErrorDetailsTest {

	ErrorDetails errorDetails = new ErrorDetails();

	@Test
	void setAccountIdTest() {
		errorDetails.setDetails("Error!");
		assertEquals("Error!", errorDetails.getDetails());
	}

	@Test
	void setAmountTest() {
		errorDetails.setMessage("Error Occured");
		assertEquals("Error Occured", errorDetails.getMessage());
	}

	@Test
	void getAccountIdTest() {
		errorDetails.setDetails("Error!");
		assertTrue(errorDetails.getDetails() == "Error!");
	}

	@Test
	void getAmountTest() {
		errorDetails.setMessage("Error Occured");
		assertTrue(errorDetails.getMessage() == "Error Occured");
	}
}
