package com.cognizant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.exception.AccessDeniedException;
import com.cognizant.feign.AuthorizationFeign;
import com.cognizant.model.AuthenticationResponse;
import com.cognizant.model.RulesInput;
import com.cognizant.model.ServiceResponse;

@Service
public class RulesServiceImpl implements RulesService {

	@Autowired
	public AuthorizationFeign authorizationFeign;
	
	private double minBalance=2000.00;
	private float serviceCharge= 550;

	@Override
	public boolean evaluate(RulesInput account) {
		double check = account.getBalance() - account.getAmount();
		if (check >= minBalance)
			return true;
		else
			return false;
	}

	@Override
	public AuthenticationResponse hasPermission(String auth) {
		AuthenticationResponse validity = authorizationFeign.getValidity("Bearer "+auth);
		if (!authorizationFeign.getRole(validity.getUserId()).equals("ADMIN"))
			throw new AccessDeniedException("Access Not Granted");
		else
			return validity;
	}

	@Override
	public ServiceResponse serviceCharges(RulesInput account) {
		ServiceResponse response = new ServiceResponse();
		response.setAccountNumber(account.getAccountNumber());
		if (account.getBalance() < 1000) {
			double detected = account.getBalance() / 2;
			response.setMessage("Your Balance is lesser than the minimum balance so " + detected
					+ " is detected from your account");
			response.setBalance(account.getBalance() - detected);
		} else {
			response.setMessage("No Detection");
			response.setBalance(account.getBalance());
		}
		return response;
	}

}
