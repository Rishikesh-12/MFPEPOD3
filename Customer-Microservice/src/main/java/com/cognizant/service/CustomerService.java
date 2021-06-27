package com.cognizant.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.model.Customer;
import com.cognizant.repository.CustomerRepository;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository repo;
	
	public int createCustomer(Customer customer) {
		repo.save(customer);
		return customer.getId();
	}
	
	public Customer getCustomer(int id) {
		Optional<Customer> customer= repo.findById(id);
		if(customer.isEmpty())
			return null;
		return customer.get();
	}
}
