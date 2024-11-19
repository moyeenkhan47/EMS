package com.emp.model;

import lombok.Data;

@Data
public class ResponseEmpRegisterDto {
	private String employeeId;
	private String fullName;
	private String email;
	private String password;
	private boolean isRegister;
	
}
