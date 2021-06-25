/**
 * 
 */
package com.cognizant.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rishikesh
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	private long accountNumber;
	private String ownerName;
	private String customerId;
	private double currentBalance;
	private String accountType;
	private List<Transaction> transactions;
}
