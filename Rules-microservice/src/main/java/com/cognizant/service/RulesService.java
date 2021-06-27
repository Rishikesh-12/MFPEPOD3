package com.cognizant.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cognizant.model.Account;
import com.cognizant.repo.AccountRepository;

@Service
public class RulesService {
	
	private double minBalance=2000.00;
	private float serviceCharge= 550;
	
	@Autowired
	private AccountRepository repo;
	
	public Optional<Account>  getAccount(String id) {
			return repo.findById(id);
		}
		
	public String evaluate(String id, double balance){
		if(balance < minBalance)
			return "Denied";
		return "Allowed";
	}
	
	public float getCharge(String id, double balance) {
		 if(balance < minBalance)
			 return serviceCharge;
		 return 0;
		
	}
}