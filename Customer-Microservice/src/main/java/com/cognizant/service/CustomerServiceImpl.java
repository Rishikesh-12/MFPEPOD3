package com.cognizant.service;

import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognizant.entities.Customer;
import com.cognizant.exception.AccessDeniedException;
import com.cognizant.exception.ConsumerAlreadyExistException;
import com.cognizant.feign.AccountFeign;
import com.cognizant.feign.AuthorizationFeign;
import com.cognizant.model.Account;
import com.cognizant.model.AuthUser;
import com.cognizant.model.AuthenticationResponse;
import com.cognizant.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Autowired
	private AuthorizationFeign authorizationFeign;

	@Autowired
	private AccountFeign accountFeign;

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Customer createCustomer(String auth, Customer customer){
		log.info("Creating Customer");
		Customer customerCheck = getCustomerDetail(auth, customer.getUserId());
		String password = customer.getPassword();
		if (customerCheck != null) {
			throw new ConsumerAlreadyExistException("Customer Already Exist");
		} else {
			AuthUser user = new AuthUser(customer.getUserId(), customer.getUsername(), passwordEncoder.encode(password),
					null, "CUSTOMER");
			authorizationFeign.createUser(user);
		}
		customer.setPassword(passwordEncoder.encode(password));
		for (Account account : customer.getAccounts()) {
			accountFeign.createAccount(auth, customer.getUserId(), account);
		}

		customerRepository.save(customer);
		log.info("Customer details saved.");
		return customer;
	}

	@Override
	public Customer getCustomerDetail(String auth, String id) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (!customer.isPresent())
			return null;
		log.info("Consumer details fetched.");
		List<Account> accounts = accountFeign.getCustomerAccount(auth, id);
		customer.get().setAccounts(accounts);
		return customer.get();
	}

	@Override
	public boolean deleteCustomer(String id) {
		Customer customer = customerRepository.findById(id).get();
		if (customer != null)
			customerRepository.deleteById(id);
		else
			return false;
		log.info("Consumer details deleted.");
		return true;
	}

	@Override
	public Customer updateCustomer(String auth, Customer customer) throws MessagingException {
		log.info("In Update Customer Service");
		Customer up = customerRepository.findById(customer.getUserId()).get();
		String password = customer.getPassword();
		up.setAddress(customer.getAddress());
		up.setDateOfBirth(customer.getDateOfBirth());
		up.setPanNumber(customer.getPanNumber());
		up.setEmail(customer.getEmail());
		up.setPassword(customer.getPassword());
		up.setUsername(customer.getUsername());
		for (Account acc : customer.getAccounts()) {
			accountFeign.updateAccount(auth, acc);
		}
		up.setAccounts(customer.getAccounts());
		return customerRepository.save(up);
	}

	@Override
	public AuthenticationResponse hasPermission(String auth) {
		return authorizationFeign.getValidity("Bearer " + auth);
	}

	@Override
	public AuthenticationResponse hasAdminPermission(String auth) {
		AuthenticationResponse validity = authorizationFeign.getValidity("Bearer " + auth);
		if (!authorizationFeign.getRole(validity.getUserId()).equals("ADMIN"))
			throw new AccessDeniedException("Access Not Granted");
		else
			return validity;
	}

	@Override
	public AuthenticationResponse hasCustomerPermission(String auth) {
		AuthenticationResponse validity = authorizationFeign.getValidity("Bearer " + auth);
		if (!authorizationFeign.getRole(validity.getUserId()).equals("CUSTOMER"))
			throw new AccessDeniedException("Access Not Granted");
		else
			return validity;
	}
}
