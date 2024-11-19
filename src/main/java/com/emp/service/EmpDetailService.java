package com.emp.service;

import java.util.List;

import com.emp.constant.EmploymentStatus;
import com.emp.model.RequestEmpDetailDto;
import com.emp.model.ResponseEmpDetailDto;

public interface EmpDetailService {
	 ResponseEmpDetailDto saveEmployee(RequestEmpDetailDto requestEmpDetailDto);
	    List<ResponseEmpDetailDto> getAllEmployees();
	    ResponseEmpDetailDto getEmployeeById(String employeeId);
	    String deleteEmployee(String employeeId);
	    public ResponseEmpDetailDto updateEmployee(String employeeId, RequestEmpDetailDto requestEmpDetailDto);
	    public  ResponseEmpDetailDto markEmployeesForExit(String employeeId,EmploymentStatus employmentStatus);
}
