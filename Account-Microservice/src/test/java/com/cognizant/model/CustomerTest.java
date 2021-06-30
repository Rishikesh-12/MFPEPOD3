package com.cognizant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;

import org.junit.jupiter.api.Test;

class CustomerTest {


	CustomerEntity entity = new CustomerEntity();
	@SuppressWarnings("deprecation")
	CustomerEntity entity2 = new CustomerEntity("Temp", "Temp313", null, "temp@gmail.com", new Date(2000, 1, 17), "EPIPB15A56",
			"Jaipur");

	@Test
	void setUserIdTest() {
		entity.setUserId("Temp");
		assertEquals("Temp", entity.getUserId());
	}

	@Test
	void setUserNameTest() {
		entity.setUsername("Temp313");
		assertEquals("Temp313", entity.getUsername());
	}
	
	@Test
	void setEmailTest() {
		entity.setUsername("temp@gmail.com");
		assertEquals("temp@gmail.com", entity.getUsername());
	}

	@Test
	void setPanNumberTest() {
		entity.setPan("EPIPB15A56");
		assertEquals("EPIPB15A56", entity.getPan());
	}

	@Test
	void setAddressTest() {
		entity.setAddress("Jaipur");
		assertEquals("Jaipur", entity.getAddress());
	}

	@SuppressWarnings("deprecation")
	@Test
	void setDateTest() {
		entity.setDateOfBirth(new Date(2000, 1, 17));
		assertEquals(new Date(2000, 1, 17), entity.getDateOfBirth());
	}

}