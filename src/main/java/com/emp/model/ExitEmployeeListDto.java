package com.emp.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExitEmployeeListDto {
    private List<Long> employeeIds; 
    private String exitReason; 
    private LocalDate exitDate;

   
}