package com.cognizant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cognizant.dto.Statement;
import com.cognizant.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	@Query("SELECT t FROM Transaction t where t.sourceAccountNumber = ?3 and t.transactionDate BETWEEN STR_TO_DATE( ?1, '%Y-%m-%d') and STR_TO_DATE( ?2, '%Y-%m-%d')")
	public List<Transaction> getStatement(String from_date, String to_date, long id);

}
