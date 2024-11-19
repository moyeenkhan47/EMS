package com.emp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

public class EmployeeRegister {
	
	private String employeeId;
	private String fullName;
	@Id
	private String email;
	private String password;
	private boolean isRegister;
	/*
	 * public EmployeeRegister(String employeeId, boolean isRegister) { super();
	 * this.employeeId = employeeId; this.isRegister = isRegister; }
	 */
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isRegister() {
		return isRegister;
	}
	public void setRegister(boolean isRegister) {
		this.isRegister = isRegister;
	}
	public EmployeeRegister(String employeeId, String fullName, String email, String password, boolean isRegister) {
		super();
		this.employeeId = employeeId;
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.isRegister = isRegister;
	}
	public EmployeeRegister() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
