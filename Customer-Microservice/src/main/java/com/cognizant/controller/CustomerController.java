package com.cognizant.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cognizant.dto.AccountCreationStatus;
import com.cognizant.dto.CreationStatus;
import com.cognizant.model.Customer;
import com.cognizant.service.CustomerService;

@RestController("/api")
public class CustomerController {
	@Autowired
	private CustomerService service;
	
	@PostMapping("/customer")
	public CreationStatus registerCustomer(@RequestBody Customer customer){
		System.out.println(customer.toString());
		int id=service.createCustomer(customer);
		String url="http://localhost:8081/api/account/"+String.valueOf(id);
		RestTemplate restTemplate =new RestTemplate();
		restTemplate.exchange(url, HttpMethod.POST, null, AccountCreationStatus.class);
		CreationStatus cs=new CreationStatus(id, "Account Created Succesfully");
		return cs;
	}
	
	@GetMapping("/customer/{id}")
	public Customer getCustomerDetails(@PathVariable("id") int id) {
		Customer customer = service.getCustomer(id);
		if(customer==null)
			return null;
		return customer;
	}
}
