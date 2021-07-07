package com.cognizant.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.entities.AuthResponse;
import com.cognizant.entities.AuthUsers;
import com.cognizant.exception.AppUserNotFoundException;
import com.cognizant.repository.UserRepository;
import com.cognizant.service.LoginService;
import com.cognizant.service.Validationservice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LoginService loginService;

	@Autowired
	private Validationservice validationService;

	@PostMapping("/login")
	public ResponseEntity<AuthUsers> login(@RequestBody AuthUsers authUser)
			throws UsernameNotFoundException, AppUserNotFoundException {
		AuthUsers user = loginService.userLogin(authUser);
		log.info("Credentials :{}", user);
		return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
	}

	@GetMapping("/validateToken")
	public AuthResponse getValidity(@RequestHeader("Authorization") final String auth) {
		log.info("Token Validation :{}", auth);
		return validationService.validate(auth);
	}

	@PostMapping("/createUser")
	public ResponseEntity<?> createUser(@RequestBody AuthUsers authUser) {
		AuthUsers user = null;
		try {
			user = userRepository.save(authUser);
		} catch (Exception e) {
			return new ResponseEntity<String>("Not created", HttpStatus.NOT_ACCEPTABLE);
		}
		log.info("user creation :{}", user);
		return new ResponseEntity<>(user, HttpStatus.CREATED);

	}

	@GetMapping("/role/{id}")
	public String getRole(@PathVariable("id") String id) {
		log.info("Getting the role");
		return userRepository.findById(id).get().getRole();
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/find")
	public ResponseEntity<List<AuthUsers>> findUsers(@RequestHeader("Authorization") final String auth) {
		List<AuthUsers> array = new ArrayList<>();
		List<AuthUsers> allUsers = userRepository.findAll();
		allUsers.forEach(emp -> array.add(emp));
		log.info("All Users :{}", allUsers);
		return new ResponseEntity<>(array, HttpStatus.CREATED);

	}

}