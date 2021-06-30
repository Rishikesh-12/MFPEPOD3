package com.cognizant.entities;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Getter
@Setter
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

	private LocalDateTime date;

	private String reference;

}