package com.emp.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.emp.entity.EmployeeLoginDto;
import com.emp.entity.EmployeeRegister;
import com.emp.exception.ResourceNotFoundException;
import com.emp.model.RequestEmpRegisterDto;
import com.emp.model.ResponseEmpRegisterDto;
import com.emp.repository.EmpRegisterRepository;
import com.emp.service.EmpRegisterService;

@Service
public class EmpRegisterServiceImpl implements EmpRegisterService {
	@Autowired
	private EmpRegisterRepository empRegisterRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public EmployeeRegister registerEmployee(RequestEmpRegisterDto requestEmpRegisterDto) {

		EmployeeRegister employeeRegister = modelMapper.map(requestEmpRegisterDto, EmployeeRegister.class);

		employeeRegister.setPassword(passwordEncoder.encode(requestEmpRegisterDto.getPassword()));

		return empRegisterRepository.save(employeeRegister);
	}

	@Override
	public List<ResponseEmpRegisterDto> getAllEmployee() {
		List<EmployeeRegister> allEmployees = empRegisterRepository.findAll();
		return allEmployees.stream().map(employee -> modelMapper.map(employee, ResponseEmpRegisterDto.class))
				.collect(Collectors.toList());
	}

	public ResponseEmpRegisterDto authenticate(EmployeeLoginDto employeeLoginDto) {
	    // Fetch the employee from the repository based on email
	    EmployeeRegister employee = empRegisterRepository.findByEmail(employeeLoginDto.getEmail())
	            .orElseThrow(() -> new ResourceNotFoundException("Resource not found with email: " + employeeLoginDto.getEmail()));

	    // Validate the password using password encoder
	    if (!passwordEncoder.matches(employeeLoginDto.getPassword(), employee.getPassword())) {
	        throw new IllegalArgumentException("Invalid password");
	    }

	    // Use ModelMapper to map EmployeeRegister to ResponseEmpRegisterDto
	    return modelMapper.map(employee, ResponseEmpRegisterDto.class);
	}
	

}
