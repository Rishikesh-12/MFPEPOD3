package com.cognizant.service;

import javax.mail.MessagingException;

import com.cognizant.entities.Customer;
import com.cognizant.model.AuthenticationResponse;

public interface CustomerService {

	public Customer createCustomer(String auth, Customer customer) throws MessagingException;

	public Customer getCustomerDetail(String auth, String userId);

	public boolean deleteCustomer(String userId);

	public Customer updateCustomer(String auth, Customer customer) throws MessagingException;

	public AuthenticationResponse hasCustomerPermission(String auth);

	public AuthenticationResponse hasAdminPermission(String auth);

	public AuthenticationResponse hasPermission(String auth);
}
