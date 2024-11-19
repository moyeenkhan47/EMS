package com.emp.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDetails {
	private String errorMessage;
	private String errorDesc;
	private Date date;

}
