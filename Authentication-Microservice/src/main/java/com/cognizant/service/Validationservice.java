package com.cognizant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cognizant.entities.AuthResponse;
import com.cognizant.repository.UserRepository;
import com.cognizant.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Validationservice {

	@Autowired
	private JwtUtil jwtutil;
	@Autowired
	private UserRepository userRepo;

	public AuthResponse validate(String auth) {
		AuthResponse authenticationResponse = new AuthResponse();
		String jwt = auth.substring(7);
		log.info(auth);
		log.info(jwt);
		if (jwtutil.validateToken(jwt)) {
			authenticationResponse.setUserId(jwtutil.extractUsername(jwt));
			authenticationResponse.setValid(true);
			authenticationResponse.setName(userRepo.findById(jwtutil.extractUsername(jwt)).get().getUsername());
		} else {
			authenticationResponse.setValid(false);
		}
		return authenticationResponse;
	}
}