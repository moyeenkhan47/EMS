package com.emp.model;

import java.time.LocalDate;

import com.emp.constant.EmploymentStatus;
import com.emp.constant.EmploymentType;

import lombok.Data;

@Data
public class ResponseEmpDetailDto {
	private String employeeId;
	private String firstName;
	private String lastName;
	private String address;
	private String country;
	private LocalDate dateOfBirth;
	private String email;
	private String phoneNumber;
	private String jobTitle;
	private LocalDate dateOfJoining;
	private EmploymentType employmentType; // Employment type (e.g., Full-time, Part-time, Contract)
	private EmploymentStatus employmentStatus; // Current status (e.g., Active, Inactive, Terminated)
	private byte[] empPhoto;
	private byte[] idProof;
}
