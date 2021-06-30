package com.cognizant.service;

import com.cognizant.model.AuthenticationResponse;
import com.cognizant.model.RulesInput;
import com.cognizant.model.ServiceResponse;

public interface RulesService {
	
	public boolean evaluate(RulesInput account);
	
	public AuthenticationResponse hasPermission(String auth);
	
	public ServiceResponse serviceCharges(RulesInput account);
	
}
