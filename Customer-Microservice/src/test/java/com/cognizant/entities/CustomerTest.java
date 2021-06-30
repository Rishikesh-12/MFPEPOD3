package com.cognizant.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;

public class CustomerTest {
	Customer customer = new Customer();

	@Test
	void setUserIdTest() {
		customer.setUserId("1");
		assertEquals("1", customer.getUserId());
	}

	@Test
	void setUserNameTest() {
		customer.setUsername("Don");
		assertEquals("Don", customer.getUsername());
	}

	@Test
	void setAddressTest() {
		customer.setAddress("Don");
		assertEquals("Don", customer.getAddress());
	}

	@Test
	void setPanNumberTest() {
		customer.setPanNumber("abc");
		assertEquals("abc", customer.getPanNumber());
	}

	@Test
	void setDateTest() {
		customer.setDateOfBirth(null);
		assertEquals(null, customer.getDateOfBirth());
	}

	@Test
	void getUserIdTest() {
		customer.setUserId("1");
		assertEquals("1", customer.getUserId());
	}

	@Test
	void getUserNameTest() {
		customer.setUsername("Don");
		assertEquals("Don", customer.getUsername());
	}

	@Test
	void getAddressTest() {
		customer.setAddress("Delhi");
		assertEquals("Delhi", customer.getAddress());
	}

	@Test
	void getPanNumberTest() {
		customer.setPanNumber("BLJPN1234R");
		assertEquals("BLJPN1234R", customer.getPanNumber());
	}

	@Test
	void getDateTest() {
		customer.setDateOfBirth(null);
		assertEquals(null, customer.getDateOfBirth());
	}
}
