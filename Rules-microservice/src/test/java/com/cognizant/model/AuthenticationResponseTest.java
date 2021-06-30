package com.cognizant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AuthenticationResponseTest {

	AuthenticationResponse response = new AuthenticationResponse();

	@Test
	void setUserIdTest() {
		response.setUserId("ADMIN");
		assertEquals("ADMIN", response.getUserId());
	}

	@Test
	void setNameTest() {
		response.setName("Joydeep");
		assertEquals("Joydeep", response.getName());
	}

	@Test
	void setisValidTest() {
		response.setValid(true);
		assertEquals(true, response.isValid());
	}

}
