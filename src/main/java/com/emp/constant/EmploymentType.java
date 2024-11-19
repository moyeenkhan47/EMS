package com.emp.constant;

public enum EmploymentType {
	FULL_TIME("Full-time"),
	PART_TIME("Part-time"),
	CONTRACT("Contract");

	private final String displayValue;

	EmploymentType(String displayValue) {
		this.displayValue = displayValue;
	}

	public String getDisplayValue() {
		return displayValue;
	}
}
