package com.emp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emp.entity.EmployeeLoginDto;
import com.emp.entity.EmployeeRegister;
import com.emp.exception.ResourceNotFoundException;
import com.emp.model.RequestEmpRegisterDto;
import com.emp.model.ResponseEmpRegisterDto;
import com.emp.service.EmpRegisterService;
import com.emp.util.EmailService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/admin")
public class EmpRegisterController {
	@Autowired
	private EmpRegisterService empRegisterService;
	@Autowired
	private EmailService emailService;
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody EmployeeLoginDto employeeLoginDto) {
	    try {
	        // Call the service to authenticate the employee
	        ResponseEmpRegisterDto authenticate = empRegisterService.authenticate(employeeLoginDto);

	        // If authentication is successful, return the response with the authenticated employee details
	        return new ResponseEntity<>(authenticate, HttpStatus.OK);
	    } catch (ResourceNotFoundException e) {
	        // Handle the case where the employee is not found
	        return new ResponseEntity<>("Employee not found with the provided email", HttpStatus.NOT_FOUND);
	    } catch (IllegalArgumentException e) {
	        // Handle invalid credentials
	        return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
	    } catch (Exception e) {
	        // Handle any other exceptions
	        return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerEmployee(@RequestBody RequestEmpRegisterDto requestEmpRegisterDto) {
	    Map<String, String> response = new HashMap<>();
	    
	    // Register the employee
	    EmployeeRegister registerEmployee = empRegisterService.registerEmployee(requestEmpRegisterDto);

	    // Prepare email content
	    String subject = "Welcome to Our Company";
	    String message = "Dear " + registerEmployee.getFullName() + ",<br><br>" +
                "Thank you for registering with us!<br>" +
                "Please use the following credentials to complete your registration:<br>" +
                "<strong>Email: " + registerEmployee.getEmail() + "</strong><br>" +
                "<strong>Password: " + requestEmpRegisterDto.getPassword() + "</strong><br><br>" +
                "Best regards,<br>Company Name"
	               
	    ;

	    // Send email with credentials
	    try {
	        emailService.sendMimeMessage(
	            registerEmployee.getEmail(), // Employee email
	            subject, 
	            message, 
	            null, // Attachments (if any)
	            null  // File name for attachment (if any)
	        );
	    } catch (MessagingException e) {
	        // Handle email sending failure
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email.");
	    }

	    // Respond with success message
	    response.put("message", "Employee registered successfully. Please check your email for the credentials.");
	    return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	@GetMapping("/all")
	public ResponseEntity<List<ResponseEmpRegisterDto>> getEmployee() {
		List<ResponseEmpRegisterDto> employeeDetail = empRegisterService.getAllEmployee();
		return new ResponseEntity<>(employeeDetail, HttpStatus.OK);
	}

	
}
