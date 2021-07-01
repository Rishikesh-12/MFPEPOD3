package com.cognizant.model;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDetailsResponse {

	private String userId;

	private String username;

	private String password;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date dateOfBirth;

	private String panNumber;

	private String address;

	private List<Account> accounts = new ArrayList<>();

}
