package com.cognizant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.cognizant.model.AccountErrorResponse;

class AccountErrorResponseTest {

	AccountErrorResponse response = new AccountErrorResponse();
	AccountErrorResponse response2 = new AccountErrorResponse(null, HttpStatus.OK, "InValid", "Not Created");

	@Test
	void setStatusTest() {
		response.setStatus(HttpStatus.OK);
		assertEquals(HttpStatus.OK, response.getStatus());
	}

	@Test
	void setReasonTest() {
		response.setReason("InValid");
		assertEquals("InValid", response.getReason());
	}

	@Test
	void setMessageTest() {
		response.setMessage("InValid");
		assertEquals("InValid", response.getMessage());
	}

	@Test
	void setTimeStampTest() {
		response.setTimestamp(null);
		assertEquals(null, response.getTimestamp());
	}
}