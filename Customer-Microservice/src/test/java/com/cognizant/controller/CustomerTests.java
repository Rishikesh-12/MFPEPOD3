package com.cognizant.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cognizant.exception.ConsumerAlreadyExistException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class CustomerTests {

	public String auth = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBRE1JTiIsImV4cCI6MTYyNDU2NDU5OSwiaWF0IjoxNjI0NTYwOTk5fQ.kDQOC68u8XkeRStkVVwCq1rKXxLxBMKxG6wwKJtJ4vk";
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wc;

	@Autowired
	CustomerController controller;
	
	static ObjectMapper mapper = new ObjectMapper();

	@Before(value = "")
	public void setUp() throws JsonProcessingException, Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wc).build();
	}

	@Test
	public void customerAlreadyExist() {
		ConsumerAlreadyExistException e1 = new ConsumerAlreadyExistException("hi");
		ConsumerAlreadyExistException e2 = new ConsumerAlreadyExistException("hi");
		assertThat(e1).isNotEqualTo(e2);
	}

}