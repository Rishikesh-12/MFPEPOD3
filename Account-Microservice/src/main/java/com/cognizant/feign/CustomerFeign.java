package com.cognizant.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cognizant.model.CustomerEntity;

@FeignClient(name = "customer-ms", url = "${feign.url-customer-ms}")
public interface CustomerFeign {

	@GetMapping("/getCustomerDetails/{userId}")
	public CustomerEntity getCustomerDetails(@RequestHeader("Authorization") String auth,
			@PathVariable(name = "userId") String userId);

}
