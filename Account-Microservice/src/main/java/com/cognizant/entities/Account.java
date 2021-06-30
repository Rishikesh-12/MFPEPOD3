package com.cognizant.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cognizant.model.Transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long accountNumber;

	@NotBlank(message = "Enter customerId")
	private String customerId;

	@NotNull(message = "Enter currentBalance")
	private double balance;

	@NotBlank(message = "Enter accountType")
	private String accountType;

	@Column(length = 20)
	@NotBlank(message = "Enter ownerName")
	private String username;

	@Transient
	private List<Transaction> transactions;

}