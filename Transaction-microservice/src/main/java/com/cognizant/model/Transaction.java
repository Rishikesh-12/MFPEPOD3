/**
 * 
 */
package com.cognizant.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rishikesh
 *
 */

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long sourceAccountNumber;
	private String sourceOwnerName;
	private long targetAccountNumber;
	private String targetOwnerName;
	private double amount;
	private Date transactionDate;
	private String reference;

}