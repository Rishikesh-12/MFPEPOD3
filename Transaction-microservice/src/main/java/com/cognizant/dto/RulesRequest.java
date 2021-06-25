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

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RulesRequest {	
	@NotNull
	private long accountNumber;
	@NotNull
	private double currentBalance;
	@NotNull
	private double amount;

}
