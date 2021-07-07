package com.cognizant.feign;

import java.util.List;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cognizant.model.Account;
import com.cognizant.model.AccountInput;

@FeignClient(name = "account-ms", url = "${feign.url-account-ms}")
public interface AccountFeign {

	@GetMapping("/find")
	public List<Account> getAllAccount();

	@PostMapping("/serviceCharge")
	public void serviceCharge(AccountInput accountInput);

}
