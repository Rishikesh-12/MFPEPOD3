package com.cognizant.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cognizant.model.Statement;

public interface StatementRepository extends JpaRepository<Statement, Integer> {

	@Query(value = "SELECT s FROM Statement s WHERE s.accountNo = ?1 and s.date between ?2 and ?3 order by s.date desc")
	  List<Statement> findStatement(long accountNo, Date fromDate, Date toDate);

}
