package com.emp.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EmploymentStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    TERMINATED("Terminated");

    private final String displayValue;

    EmploymentStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    @JsonValue // Ensures `displayValue` is used during serialization
    public String getDisplayValue() {
        return displayValue;
    }

    @JsonCreator // Ensures `displayValue` is used during deserialization
    public static EmploymentStatus fromDisplayValue(String displayValue) {
        for (EmploymentStatus status : EmploymentStatus.values()) {
            if (status.displayValue.equalsIgnoreCase(displayValue)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid EmploymentStatus: " + displayValue);
    }
}
