/**
 * 
 */
package com.cognizant.exception;

/**
 * @author Rishikesh
 *
 */
import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionErrorResponse {
	/**
	 *  TransactionErrorResponse for returning Error response
	 */
	
	private Date date;
	private HttpStatus status;
	private String reason;
	private String message;
}
