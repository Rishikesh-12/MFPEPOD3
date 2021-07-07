package com.cognizant.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cognizant.exception.AccessDeniedException;
import com.cognizant.feign.AuthorizationFeign;
import com.cognizant.model.AuthenticationResponse;
import com.cognizant.model.RulesInput;
import com.cognizant.model.ServiceResponse;

@ExtendWith(SpringExtension.class)
class RulesServiceImplTest {

	@InjectMocks
	RulesServiceImpl serviceImpl;

	@Mock
	AuthorizationFeign authFeignClient;

	@Test
	void evaluateTest() {
		RulesServiceImpl service = new RulesServiceImpl();
		RulesInput in = new RulesInput(1000, 10000, 10);
		assertEquals(true, service.evaluate(in));
	}

	@Test
	void evaluateTest2() {
		RulesServiceImpl service = new RulesServiceImpl();
		RulesInput in = new RulesInput(1000, 100, 10);
		assertEquals(true, !service.evaluate(in));
	}

	@Test
	void evaluateTest3() {
		RulesServiceImpl service = new RulesServiceImpl();
		RulesInput in = new RulesInput(1000, 1000, 100);
		assertEquals(true, !service.evaluate(in));
	}

	@Test
	void hasPermissionTest1() {
		when(authFeignClient.getValidity("Bearer " + "token")).thenReturn(new AuthenticationResponse("ADMIN", "ADMIN", true));
		when(authFeignClient.getRole("ADMIN")).thenReturn("ADMIN");
		assertTrue(serviceImpl.hasPermission("token").isValid());
	}

	@Test
	void hasPermissionTest2() {
		when(authFeignClient.getValidity("Bearer " + "token")).thenThrow(new AccessDeniedException("Access Not Granted"));
		assertThrows(AccessDeniedException.class, () -> serviceImpl.hasPermission("token"));
	}

	@Test
	void servicechargeTest1() {
		RulesInput in = new RulesInput(1000, 10000, 10);
		ServiceResponse get = serviceImpl.serviceCharges(in);
		ServiceResponse res = new ServiceResponse("No Detection", 1000, (double) 10000);
		assertEquals(get.getMessage(), res.getMessage());
	}

	@Test
	void servicechargeTest3() {
		RulesInput inp = new RulesInput(101, 100, 100);
		ServiceResponse get = serviceImpl.serviceCharges(inp);
		String str = "Your Balance is lesser than the minimum balance so 50.0 is detected from your account";
		ServiceResponse res = new ServiceResponse(str, 101, (double) 50);
		assertEquals(get.getBalance(), res.getBalance());
	}

}