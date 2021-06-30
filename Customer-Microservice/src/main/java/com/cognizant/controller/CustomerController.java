package com.cognizant.controller;

import java.net.BindException;
import java.time.DateTimeException;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.entities.Customer;
import com.cognizant.service.CustomerServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/bankapi")
public class CustomerController {

	@Autowired
	private CustomerServiceImpl customerServiceImpl;

	@PostMapping("/createCustomer")
	public ResponseEntity<?> createCustomer(@RequestHeader("Authorization") String auth,
			@Valid @RequestBody Customer customer)throws DateTimeException{
		log.info("Creating a customer");
		log.info(auth);
		customerServiceImpl.hasAdminPermission(auth);
		Customer newCustomer = customerServiceImpl.createCustomer(auth, customer);
		if (newCustomer != null)
			return new ResponseEntity<>(true, HttpStatus.CREATED);
		else
			return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
	}

	@PostMapping("/updateCustomer")
	public Customer updateCustomer(@RequestHeader("Authorization") String auth, @Valid @RequestBody Customer customer) throws MessagingException {
		log.info("Updating the customer");
		customerServiceImpl.hasAdminPermission(auth);
		return customerServiceImpl.updateCustomer(auth, customer);
	}

	@GetMapping("/getCustomerDetails/{userId}")
	public ResponseEntity<?> getCustomerDetails(@RequestHeader("Authorization") String auth,
			@PathVariable String userId) {
		log.info("Getting Customer Details");
		customerServiceImpl.hasPermission(auth);
		Customer toReturnCustomerDetails = customerServiceImpl.getCustomerDetail(auth, userId);
		if (toReturnCustomerDetails == null)
			return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
		toReturnCustomerDetails.setPassword(null);
		return new ResponseEntity<>(toReturnCustomerDetails, HttpStatus.OK);
	}

	@DeleteMapping("deleteCustomer/{userId}")
	public ResponseEntity<?> deleteCustomer(@RequestHeader("Authorization") String auth, @PathVariable String userId) {
		log.info("Deleting Customer Details");
		customerServiceImpl.hasAdminPermission(auth);

		Customer check = null;
		check = customerServiceImpl.getCustomerDetail(auth, userId);
		if (check == null) {
			return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
		}

		log.debug("Starting deletion of {}",userId);
		customerServiceImpl.deleteCustomer(userId);
		log.info("Deleted");
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

}
