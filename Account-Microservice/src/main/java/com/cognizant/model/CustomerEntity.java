package com.cognizant.model;

import java.sql.Date;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {
	private String userId;
	private String username;
	private String password;
	private String email;
	private Date dateOfBirth;
	private String pan;
	private String address;
}