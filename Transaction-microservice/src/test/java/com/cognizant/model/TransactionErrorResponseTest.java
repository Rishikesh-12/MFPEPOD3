package com.cognizant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.cognizant.models.TransactionErrorResponse;

class TransactionErrorResponseTest {
	
	TransactionErrorResponse errorResponse = new TransactionErrorResponse();
	
//	Setters
	
	@Test
	void setTimeStampTest() {
		errorResponse.setTimestamp(LocalDateTime.of(2021, 6, 18, 12, 55));
		assertEquals(LocalDateTime.of(2021, 6, 18, 12, 55), errorResponse.getTimestamp());
	}
	
	@Test
	void setStatusTest() {
		errorResponse.setStatus(HttpStatus.FORBIDDEN);
		assertEquals(HttpStatus.FORBIDDEN, errorResponse.getStatus());
	}
	
	@Test
	void setReasonTest() {
		errorResponse.setReason("Forbidden");
		assertEquals("Forbidden", errorResponse.getReason());
	}
	
	@Test
	void setMessageTest() {
		errorResponse.setMessage("Forbidden");
		assertEquals("Forbidden", errorResponse.getMessage());
	}
	
//	Getters
	
	@Test
	void getTimeStampTest() {
		errorResponse.setTimestamp(LocalDateTime.of(2021, 6, 18, 12, 55));
		assertEquals(LocalDateTime.of(2021, 6, 18, 12, 55), errorResponse.getTimestamp());
	}
	
	@Test
	void getStatusTest() {
		errorResponse.setStatus(HttpStatus.FORBIDDEN);
		assertTrue(HttpStatus.FORBIDDEN == errorResponse.getStatus());
	}
	
	@Test
	void getReasonTest() {
		errorResponse.setReason("Forbidden");
		assertTrue("Forbidden" == errorResponse.getReason());
	}
	
	@Test
	void getMessageTest() {
		errorResponse.setMessage("Forbidden");
		assertTrue("Forbidden" == errorResponse.getMessage());
	}

}
