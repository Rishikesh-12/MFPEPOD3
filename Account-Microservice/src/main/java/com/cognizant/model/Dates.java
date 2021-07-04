package com.cognizant.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Dates {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date from_date;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date to_date;
}
