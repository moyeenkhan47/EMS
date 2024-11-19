package com.emp.service;

import java.util.List;

import com.emp.entity.EmployeeLoginDto;
import com.emp.entity.EmployeeRegister;
import com.emp.model.RequestEmpRegisterDto;
import com.emp.model.ResponseEmpRegisterDto;

public interface EmpRegisterService {
	public EmployeeRegister registerEmployee(RequestEmpRegisterDto employeeRegisterDto);
	public List<ResponseEmpRegisterDto> getAllEmployee();
	 public ResponseEmpRegisterDto authenticate(EmployeeLoginDto employeeLoginDto);
	

}
