package com.emp.model;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import com.emp.constant.EmploymentStatus;
import com.emp.constant.EmploymentType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestEmpDetailDto {
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
	private MultipartFile empPhoto;
	private MultipartFile idProof;

}
