package com.cognizant.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.model.Account;

public interface AccountRepository extends JpaRepository<Account, String>{
	
	public Optional<Account> findById(String id);

}
