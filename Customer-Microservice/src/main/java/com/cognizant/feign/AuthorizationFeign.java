package com.cognizant.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cognizant.model.AuthUser;
import com.cognizant.model.AuthenticationResponse;

@FeignClient(name = "authentication-ms", url = "${feign.url-authentication-ms}")
@Component
public interface AuthorizationFeign {

	// Create Consumer
	@PostMapping(value = "/createUser")
	public ResponseEntity<?> createUser(@RequestBody AuthUser appUserCredentials);

	// Customer login
	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody AuthUser appUserloginCredentials);

	@GetMapping(value = "/validateToken")
	public AuthenticationResponse getValidity(@RequestHeader("Authorization") String token);

	@GetMapping("/role/{id}")
	public String getRole(@PathVariable("id") String id);

}
