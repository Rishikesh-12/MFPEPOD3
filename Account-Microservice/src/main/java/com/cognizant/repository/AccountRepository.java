package com.cognizant.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognizant.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>{
	
	public List<Account> findByCustId(int id);
	
	@Transactional
	@Modifying
	@Query("Update Account a set a.balance = a.balance + ?2 where a.id = ?1")
	public void updateAccountDeposit(String id,double amount);
	
	@Transactional
	@Modifying
	@Query("Update Account a set a.balance = a.balance - ?2 where a.id = ?1")
	public void updateAccountWithdrawal(String id,double amount);
	
}
