package com.cognizant.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import com.cognizant.model.Account;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {
	@Id
	@Column(name = "userid", length = 25, unique = true)
	private String userId;

	@Column(name = "username", length = 25)
	@NotBlank
	private String username;
	
	private String email;

	@Column(name = "password")
	@NotBlank
	private String password;

	@Column(name = "dateOfBirth")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date dateOfBirth;

	@Column(name = "pannumber", length = 10)
	@NotBlank
	private String panNumber;

	@Column(name = "address")
	@NotBlank
	private String address;

	@Transient
	private List<Account> accounts = new ArrayList<>();

}
