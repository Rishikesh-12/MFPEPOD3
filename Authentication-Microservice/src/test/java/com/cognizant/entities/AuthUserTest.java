package com.cognizant.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;

class AuthUsersTest {
	
	AuthUsers authUser = new AuthUsers();
	AuthResponse authResponse = new AuthResponse();

	@Test
	public void setterIdTestNeg() throws NoSuchFieldException, IllegalAccessException {
		AuthUsers authUserNew = new AuthUsers();
		authUserNew.setUserId("USERID01");
		Field field = authUserNew.getClass().getDeclaredField("userId");
		field.setAccessible(true);
		assertNotEquals("Fields doesn't match", field.get(authUserNew), "USERID");
	}

	@Test
	public void getterIdTestNeg() throws NoSuchFieldException, IllegalAccessException {
		AuthUsers authUserNew = new AuthUsers();
		Field field = authUserNew.getClass().getDeclaredField("userId");
		field.setAccessible(true);
		field.set(authUserNew, "values");
		String result = authUserNew.getUserId();
		assertNotEquals("Incorrect data", result, "NEWVALUE");
	}

	@Test
	public void getterPasswordTestNeg() throws NoSuchFieldException, IllegalAccessException {
		AuthUsers authUserNew = new AuthUsers();
		Field field = authUserNew.getClass().getDeclaredField("password");
		field.setAccessible(true);
		field.set(authUserNew, "PASSWORD123");
		String result = authUserNew.getPassword();
		assertNotEquals("PASSWORDS DO NOT MATCH", result, "PASSWORD");
	}

	@Test
	void setRoleTest() {
		authUser.setRole("CUSTOMER");
		assertEquals("CUSTOMER", authUser.getRole());
	}

	@Test
	void getRoleTest() {
		authUser.setRole("CUSTOMER");
		assertTrue(authUser.getRole() == "CUSTOMER");
	}

	@Test
	void setUsername() {
		authUser.setUsername("USERNAME");
		assertEquals("USERNAME", authUser.getUsername());
	}

	@Test
	void getUsername() {
		authUser.setUsername("USERNAME");
		assertTrue(authUser.getUsername() == "USERNAME");
	}

	@Test
	void setUserIdTest() {
		authResponse.setUserId("CUSTOMER1");
		assertEquals("CUSTOMER1", authResponse.getUserId());
	}

	@Test
	void getUserIdTest() {
		authResponse.setUserId("CUSTOMER1");
		assertTrue(authResponse.getUserId() == "CUSTOMER1");
	}

	@Test
	void setUserameTest() {
		authResponse.setName("CUSTOMER1");
		assertEquals("CUSTOMER1", authResponse.getName());
	}

	@Test
	void getUsernameTest() {
		authResponse.setName("CUSTOMER1");
		assertTrue(authResponse.getName() == "CUSTOMER1");
	}

	@Test
	void setisValidTest() {
		authResponse.setValid(true);
		assertEquals(true, authResponse.isValid());
	}

}