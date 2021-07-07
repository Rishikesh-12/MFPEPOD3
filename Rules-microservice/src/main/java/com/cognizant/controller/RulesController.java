package com.cognizant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.exception.MinimumBalanceException;
import com.cognizant.feign.AccountFeign;
import com.cognizant.model.Account;
import com.cognizant.model.AccountInput;
import com.cognizant.model.RulesInput;
import com.cognizant.service.RulesServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/bankapi")
public class RulesController {

	@Autowired
	public RulesServiceImpl rulesService;
	
	@Autowired
	public AccountFeign accountFeign;

	@PostMapping("/evaluateMinBal")
	public ResponseEntity<?> evaluate(@RequestBody RulesInput account) throws MinimumBalanceException {
		log.info("Evaluating Balance");
		if (account.getBalance() == 0) {
			throw new MinimumBalanceException("Enter Valid Details.");
		} else {
			boolean status = rulesService.evaluate(account);

			return new ResponseEntity<Boolean>(status, HttpStatus.OK);
		}
	}

	@Scheduled(cron="0 0 9 LW * *")
	public void serviceCharges() {
		log.info("Deducting Monthly Service Charge");
		try {
			List<Account> accounts = accountFeign.getAllAccount();
			for (Account account : accounts) {
				double deduct  = account.getBalance() * 0.05;
				if (account.getBalance() < 2500 && (account.getBalance() - deduct) > 0)
					accountFeign.serviceCharge(new AccountInput(account.getAccountNumber(), deduct));
			}
			log.info("Deducted service charge");

		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("I am running");
	}

	public ResponseEntity<String> evalMinimumBalanceFallback() {
		return new ResponseEntity<String>("Minimum balance criteria fail", HttpStatus.BAD_GATEWAY);
	}

}
