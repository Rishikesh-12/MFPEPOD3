package com.cognizant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.cognizant.entities.AuthUsers;
import com.cognizant.exception.AppUserNotFoundException;
import com.cognizant.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginService {

	@Autowired
	private JwtUtil jwtutil;
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private CustomerDetailsService customerDetailservice;

	public AuthUsers userLogin(AuthUsers authUser) throws AppUserNotFoundException {
		final UserDetails userdetails = customerDetailservice.loadUserByUsername(authUser.getUserId());
		String userid = "";
		String role = "";
		String token = "";
		if(encoder.matches(authUser.getPassword(),userdetails.getPassword())){
			userid = authUser.getUserId();
			token = jwtutil.generateToken(userdetails);
			role = authUser.getRole();
			return new AuthUsers(userid, null, null, token, role);
		} else {
			throw new AppUserNotFoundException("Username or Password is incorrect...Please try again.");
		}
	}
}