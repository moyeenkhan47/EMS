package com.emp.entity;

import java.time.LocalDate;

import com.emp.constant.EmploymentStatus;
import com.emp.constant.EmploymentType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetail {
	@Id
	
	private String employeeId;
	private String firstName;
	private String lastName;
	private String address;
	private String country;
	@Temporal(value = TemporalType.DATE)
	private LocalDate dateOfBirth;
	private String email;
	private String phoneNumber;
	private String jobTitle;
	@Temporal(value = TemporalType.DATE)
	private LocalDate dateOfJoining;
	@Enumerated(EnumType.STRING)
	private EmploymentType employmentType; // Employment type (e.g., Full-time, Part-time, Contract)
	@Enumerated(EnumType.STRING)
	private EmploymentStatus employmentStatus; // Current status (e.g., Active, Inactive, Terminated)
	@Lob
	private byte[] empPhoto;
	@Lob
	private byte[] idProof;

}
