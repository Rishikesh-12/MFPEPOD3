/**
 * 
 */
package com.cognizant.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cognizant.dto.RulesRequest;
import com.cognizant.exception.MinimumBalanceException;

/**
 * @author Rishikesh
 *
 */

@FeignClient(name = "rule-microservice", url = "${feign.url-rule-microservice}")
public interface RuleFeign {

	@PostMapping("/evaluateMinBal")
	public ResponseEntity<?> evaluate(@RequestBody RulesRequest rulesRequest) throws MinimumBalanceException;
}
