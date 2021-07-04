package com.cognizant.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySourceAccountNumberOrTargetAccountNumberOrderByDate(long sourceAccountNumber , long targetAccountNumber);

	List<Transaction> findByTargetAccountNumberOrderByDate(long accountNumber);

	List<Transaction> findBySourceAccountNumberOrderByDate(long accountNumber);
}
