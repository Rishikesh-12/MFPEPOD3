package com.cognizant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ErrorDetailsTest {

	ErrorDetails errors  = new ErrorDetails();

	@Test
	void setUserIdTest() {
		errors.setDetails("/notresponding");
		assertEquals("/notresponding", errors.getDetails());
	}

	@Test
	void setNameTest() {
		errors.setMessage("Joydeep");
		assertEquals("Joydeep", errors.getMessage());
	}
}
