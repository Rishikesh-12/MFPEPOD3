package com.cognizant.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import com.cognizant.repository.AccountRepository;
import com.cognizant.entities.Account;
import com.cognizant.exceptions.AccountNotFoundException;
import com.cognizant.feign.AuthFeign;
import com.cognizant.feign.TransactionFeign;
import com.cognizant.model.AccountCreationStatus;
import com.cognizant.model.AccountInput;

@ExtendWith(SpringExtension.class)
class AccountServiceTest {

	@InjectMocks
	AccountServiceImpl accountServiceImpl;

	@Mock
	AuthFeign authFeignClient;

	@Mock
	AccountRepository accountRepository;

	@Mock
	TransactionFeign transactionFeign;

	@Before(value = "")
	public void init() {
		MockitoAnnotations.initMocks(this);
    }

	@Test
	void getAccountTestCorrect() {
		Account account = new Account(1l, "Temp", 3000.0, "Savings", "Temp313", null);
		when(accountRepository.findByAccountNumber(1l)).thenReturn(account);

		assertEquals("Temp313", accountServiceImpl.getAccount(1l).getUsername());
	}

	@Test
	void getAccountTestException() {
		when(accountRepository.findByAccountNumber(2l)).thenThrow(new AccountNotFoundException());
		assertThrows(AccountNotFoundException.class, () -> accountServiceImpl.getAccount(2));
	}

	@Test
	void getAccountTestExceptionMessage() {
		when(accountRepository.findByAccountNumber(2l)).thenThrow(new AccountNotFoundException("Account Does Not Exist"));
		assertThrows(AccountNotFoundException.class, () -> accountServiceImpl.getAccount(2));
	}

	@Test
	void getCustomerAccount() {

		List<Account> accountList = new ArrayList<>();
		Account account1 = new Account(1l, "Temp", 3000.0, "Savings", "Temp313", null);
		Account account2 = new Account(2l, "Temp", 5000.0, "Current", "Temp313", null);

		accountList.add(account1);
		accountList.add(account2);

		when(accountRepository.findByCustomerId("Temp")).thenReturn(accountList);
		when(transactionFeign.getTransactionsByAccountNumber("auth", 1)).thenReturn(null);
		when(transactionFeign.getTransactionsByAccountNumber("auth", 2)).thenReturn(null);
		assertEquals(2, accountServiceImpl.getCustomerAccount("auth", "Temp").size());
	}

	@Test
	void createAccount() {
		Account account1 = new Account(1l, "Temp", 3000.0, "Savings", "Temp313", null);
		when(accountRepository.save(account1)).thenReturn(account1);
		AccountCreationStatus status = accountServiceImpl.createAccount("Temp", null, account1);
		assertEquals("Account Created Sucessfully", status.getMessage());
	}

	@Test
	void updateBalanceTest() {
		Account account1 = new Account(1l, "Temp", 4000.0, "Savings", "Temp313", null);
		Account account2 = new Account(1l, "Temp", 3500.0, "Savings", "Temp313", null);
		when(accountRepository.findByAccountNumber(1l)).thenReturn(account1);
		when(accountRepository.save(account1)).thenReturn(account2);
		AccountInput ai = new AccountInput(1, 500);
		Account testAccount = accountServiceImpl.updateBalance(ai);
		assertEquals(3500, testAccount.getBalance());
	}

	@Test
	void updateDepositBalanceTest() {
		Account account1 = new Account(1l, "Temp", 3000.0, "Savings", "Temp313", null);
		Account account2 = new Account(1l, "Temp", 3500.0, "Savings", "Temp313", null);
		when(accountRepository.findByAccountNumber(1l)).thenReturn(account1);
		when(accountRepository.save(account1)).thenReturn(account2);
		AccountInput ai = new AccountInput(1, 500);
		Account testAccount = accountServiceImpl.updateDepositBalance(ai);
		assertEquals(3500, testAccount.getBalance());
	}
}