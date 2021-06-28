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

import com.cognizant.dto.RulesRequest;
import com.cognizant.dto.RulesStatus;
import com.cognizant.model.Account;
import com.cognizant.service.RulesService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/bankapi/")
@AllArgsConstructor
public class RulesController {

	@Autowired
	private final RulesService ruleService;

	@GetMapping("/evaluateMinBal")
	public ResponseEntity<String> evaluate(@RequestBody RulesRequest rulesRequest) {
		boolean status  = ruleService.evaluate(rulesRequest);
		String rulesStatus;
		if (status == true) rulesStatus = "allowed"; 
		else rulesStatus = "denied";
		return new ResponseEntity<>(rulesStatus,HttpStatus.OK);
		
	}
	@GetMapping("/getServiceCharges")
	@ResponseBody
	public float serviceCharges(@PathVariable String id, @PathVariable double balance) {
		
		float charge= ruleService.getCharge(id,balance);
		return charge;
		
	}
		
		
}
