package com.cognizant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AuthUsersTest {

	AuthUsers authUser = new AuthUsers();

	@Test
	void setAccountIdTest() {
		authUser.setUsername("Joydeep");
		assertEquals("Joydeep", authUser.getUsername());
	}

	@Test
	void setAmountTest() {
		authUser.setUserId("ADMIN");
		assertEquals("ADMIN", authUser.getUserId());
	}
}
