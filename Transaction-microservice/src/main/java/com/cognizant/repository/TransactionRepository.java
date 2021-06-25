package com.cognizant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	List<Transaction> findBySourceAccountNumberOrTargetAccountNumberOrderByTransactionDate(long sourceAccountNumber,
			long targetAccountNumber);
}
