package com.cognizant.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cognizant.model.AuthenticationResponse;

@FeignClient(name = "authentication-ms", url = "${feign.url-authentication-ms}")
public interface AuthorizationFeign {

	@GetMapping(value = "/validateToken")
	public AuthenticationResponse getValidity(@RequestHeader("Authorization") String auth);

	@GetMapping("/role/{userId}")
	public String getRole(@PathVariable("userId") String userId);

}
