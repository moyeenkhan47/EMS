package com.emp.service.Impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.constant.EmploymentStatus;
import com.emp.entity.EmployeeDetail;
import com.emp.entity.EmployeeRegister;
import com.emp.exception.ResourceNotFoundException;
import com.emp.model.RequestEmpDetailDto;
import com.emp.model.ResponseEmpDetailDto;
import com.emp.repository.EmpDetailRepository;
import com.emp.repository.EmpRegisterRepository;
import com.emp.service.EmpDetailService;

@Service
public class EmpDetailServiceImpl implements EmpDetailService {

	@Autowired
	private EmpDetailRepository empDetailRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private EmpRegisterRepository empRegisterRepository;

	public String generateEmployeeId() {
		int randomNum = (int) (Math.random() * 900000) + 100000; // generates a random 6-digit number
		return String.valueOf(randomNum);
	}

	@Override
	public ResponseEmpDetailDto saveEmployee(RequestEmpDetailDto requestEmpDetailDto) {
		EmployeeDetail employeeDetail = modelMapper.map(requestEmpDetailDto, EmployeeDetail.class);
		if (requestEmpDetailDto.getEmployeeId() == null || requestEmpDetailDto.getEmployeeId().isEmpty()) {
			String employeeId;
			do {
				employeeId = generateEmployeeId();
			} while (empRegisterRepository.findByEmail(employeeId).isPresent()); // Ensure the ID is unique

			employeeDetail.setEmployeeId(employeeId);
		}

		convertMultipartFiles(requestEmpDetailDto, employeeDetail);
		EmployeeDetail savedEmployee = empDetailRepository.save(employeeDetail);

		Optional<EmployeeRegister> employeeRegisterOpt = empRegisterRepository.findByEmail(savedEmployee.getEmail());

		if (employeeRegisterOpt.isPresent()) {
			EmployeeRegister employeeRegister = employeeRegisterOpt.get();
			employeeRegister.setEmployeeId(savedEmployee.getEmployeeId()); // Update employeeId
			employeeRegister.setRegister(true); // Set isRegister to true
			empRegisterRepository.save(employeeRegister);
		}
		return modelMapper.map(savedEmployee, ResponseEmpDetailDto.class);
	}

	@Override
	public List<ResponseEmpDetailDto> getAllEmployees() {
		List<EmployeeDetail> employeeDetails = empDetailRepository.findAll();
		return employeeDetails.stream().map(employee -> modelMapper.map(employee, ResponseEmpDetailDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public ResponseEmpDetailDto getEmployeeById(String employeeId) {
		EmployeeDetail employeeDetail = empDetailRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + employeeId));
		System.out.println("fetch from database");
		return modelMapper.map(employeeDetail, ResponseEmpDetailDto.class);
	}

	@Override
	public String deleteEmployee(String employeeId) {
		EmployeeDetail employeeDetail = empDetailRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + employeeId));
		empDetailRepository.deleteById(employeeId);
		empRegisterRepository.deleteById(employeeDetail.getEmail());
		return "Employee with id " + employeeId + " has been deleted successfully.";
	}

	@Override
	public ResponseEmpDetailDto updateEmployee(String employeeId, RequestEmpDetailDto requestEmpDetailDto) {
		EmployeeDetail existingEmployee = empDetailRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + employeeId));

		modelMapper.map(requestEmpDetailDto, existingEmployee);
		convertMultipartFiles(requestEmpDetailDto, existingEmployee);
		EmployeeDetail updatedEmployee = empDetailRepository.save(existingEmployee);
		return modelMapper.map(updatedEmployee, ResponseEmpDetailDto.class);
	}

	private void convertMultipartFiles(RequestEmpDetailDto requestEmpDetailDto, EmployeeDetail employeeDetail) {
		try {
			if (requestEmpDetailDto.getEmpPhoto() != null && !requestEmpDetailDto.getEmpPhoto().isEmpty()) {

				byte[] empPhotoBytes = requestEmpDetailDto.getEmpPhoto().getBytes();
				employeeDetail.setEmpPhoto(empPhotoBytes); // Set byte array directly
			}
			if (requestEmpDetailDto.getIdProof() != null && !requestEmpDetailDto.getIdProof().isEmpty()) {
				byte[] idProofBytes = requestEmpDetailDto.getIdProof().getBytes();
				employeeDetail.setIdProof(idProofBytes); // Set byte array directly
			}
		} catch (IOException e) {
			throw new RuntimeException("Error converting file to byte array", e);
		}
	}

	public  ResponseEmpDetailDto markEmployeesForExit(String employeeId,EmploymentStatus employmentStatus) {
	Optional<EmployeeDetail> byId = empDetailRepository.findById(employeeId);
		        if (!byId.isPresent()) {
		            throw new IllegalArgumentException("No active employees found with the provided IDs.");
		        }
		        EmployeeDetail employee = byId.get();
		        employee.setEmploymentStatus(EmploymentStatus.TERMINATED);
		        EmployeeDetail save = empDetailRepository.save(employee);
				return modelMapper.map(save, ResponseEmpDetailDto.class);
		    }

	

}
