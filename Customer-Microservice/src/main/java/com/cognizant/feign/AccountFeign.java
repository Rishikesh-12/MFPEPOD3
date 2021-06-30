package com.cognizant.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cognizant.model.Account;
import com.cognizant.model.AccountCreationStatus;

@FeignClient(name = "account-ms", url = "${feign.url-account-ms}")
public interface AccountFeign {

	@PostMapping("/createAccount/{customerId}")
	public AccountCreationStatus createAccount(@RequestHeader("Authorization") String auth,
			@PathVariable String customerId, @RequestBody Account account);

	@GetMapping("/getAccounts/{customerId}")
	public List<Account> getCustomerAccount(@RequestHeader("Authorization") String auth,
			@PathVariable String customerId);

	@PutMapping("/updateAccount")
	public Account updateAccount(@RequestHeader("Authorization") String auth, @RequestBody Account acc);

}
