package com.cognizant.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cognizant.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySourceAccountNumberOrTargetAccountNumberOrderByDate(long sourceAccountNumber , long targetAccountNumber);

	List<Transaction> findByTargetAccountNumberOrderByDate(long accountNumber);

	List<Transaction> findBySourceAccountNumberOrderByDate(long accountNumber);
	
	@Query("SELECT t FROM Transaction t where t.sourceAccountNumber = ?3 and t.date BETWEEN TO_DATE( ?1 , 'DD-MM-YYYY') and TO_DATE( ?2 , 'DD-MM-YYYY')")
	public List<Transaction> getStatement(String from_date, String to_date, long id);
}
