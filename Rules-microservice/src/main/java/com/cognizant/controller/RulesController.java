package com.cognizant.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.dto.RulesStatus;
import com.cognizant.model.Account;
import com.cognizant.service.RulesService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/bankapi/")
@AllArgsConstructor
public class RulesController {

	@Autowired
	private final RulesService service;

	@GetMapping("/evaluateMinBal/{id}/{balance}")
	@ResponseBody
	public RulesStatus evaluate(@PathVariable String id, @PathVariable double balance) {
		Optional<Account> acc = service.getAccount(id);
		if (acc.isEmpty())
			return new RulesStatus("Not Available");
		else {
			
			String status= service.evaluate(id, balance);
			return new RulesStatus(status);
			
		}
	}
	@GetMapping("/getServiceCharges/{id}/{balance}")
	@ResponseBody
	public float serviceCharges(@PathVariable String id, @PathVariable double balance) {
		
		float charge= service.getCharge(id,balance);
		return charge;
		
	}
		
		
}
