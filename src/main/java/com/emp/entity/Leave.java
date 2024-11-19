package com.emp.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Leave {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String employeeName;
private String email;
	@Enumerated(EnumType.STRING)
	private LeaveType leaveType;

	private LocalDate startDate;
	private LocalDate endDate;

	private String status;

	public enum LeaveType {
		SICK, VACATION, PERSONAL
	}// Example: "Pending", "Approved", "Rejected"
}