package com.cognizant.controller;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cognizant.entities.Account;
import com.cognizant.feign.AuthFeign;
import com.cognizant.feign.TransactionFeign;
import com.cognizant.model.AccountCreationStatus;
import com.cognizant.model.AuthenticationResponse;
import com.cognizant.repository.AccountRepository;
import com.cognizant.repository.StatementRepository;
import com.cognizant.service.AccountServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
@ExtendWith(SpringExtension.class)
class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthFeign authFeign;

	@MockBean
	private AccountServiceImpl accountServiceImpl;

	@MockBean
	private AccountRepository accountRepository;

	@MockBean
	private StatementRepository statementRepository;
	
	
	@MockBean
	private TransactionFeign transactionFeign;

	@Test
	void getAccountTest() throws Exception {
		when(accountServiceImpl.hasPermission("auth")).thenReturn(new AuthenticationResponse("", "", true));
		Account account = new Account();
		when(accountServiceImpl.getAccount(1)).thenReturn(account);
		mockMvc.perform(get("/bankapi/getAccount/1").header("Authorization", "auth")).andExpect(status().isOk());
		verify(accountServiceImpl, timeout(1)).getAccount(1);
	}

	@Test
	void getCustomerAccountTest() throws Exception {
		when(accountServiceImpl.hasPermission("auth")).thenReturn(new AuthenticationResponse("", "", true));
		when(accountServiceImpl.getCustomerAccount("auth", "Rishikesh")).thenReturn(new ArrayList<>());
		mockMvc.perform(get("/bankapi/getAccounts/Rishikesh").header("Authorization", "auth")).andExpect(status().isOk());
		verify(accountServiceImpl, timeout(1)).getCustomerAccount("auth", "Rishikesh");
	}

	@Test
	void createAccountTest() throws Exception {
		when(accountServiceImpl.hasAdminPermission("auth"))
				.thenReturn(new AuthenticationResponse("Emp101", "emp", true));
		Account account = new Account(1l, "Rishikesh", 2000.0, "Savings", "Samuel F", null);
		when(accountServiceImpl.createAccount("Rishikesh", null, account))
				.thenReturn(new AccountCreationStatus(1, "Sucessfully Created"));
		mockMvc.perform(MockMvcRequestBuilders.post("/bankapi/createAccount/Rishikesh").content(asJsonString(account))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "auth")).andExpect(status().isNotAcceptable());
		verify(accountServiceImpl, timeout(1)).hasAdminPermission("auth");
	}

	public static String asJsonString(final Object obj) throws Exception{
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;	
	}
}