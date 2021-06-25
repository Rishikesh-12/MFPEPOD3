/**
 * 
 */
package com.cognizant.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rishikesh
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositOrWithdrawRequest {
	@NotNull(message = "Account number is mandatory")
	private long accountNumber;
	@NotNull(message = "Amount is mandatory")
	private double amount;
}
