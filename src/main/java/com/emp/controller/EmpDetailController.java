package com.emp.controller;

import java.util.List;
import java.util.Map;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emp.constant.EmploymentStatus;
import com.emp.model.RequestEmpDetailDto;
import com.emp.model.ResponseEmpDetailDto;
import com.emp.service.EmpDetailService;
import com.emp.util.EmailService;
import com.emp.util.PdfGeneratorForEmployeeDetail;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employee")
@Tag(description = "Manage API for Employee", name = "Employee")
public class EmpDetailController {

    @Autowired
    private EmpDetailService empDetailService;

    @Autowired
    private EmailService emailService;

    @PostMapping(value="/register",consumes = "multipart/form-data")
    @Operation(summary = "Add Employee", description = "Ensure all fields are added properly")
    public ResponseEntity<ResponseEmpDetailDto> createEmployee(@Valid @ModelAttribute RequestEmpDetailDto requestEmpDetailDto) {
        // Save the employee and get the ResponseEmployeeDto
        ResponseEmpDetailDto savedEmployee = empDetailService.saveEmployee(requestEmpDetailDto);

        // Generate PDF from the saved employee details
        byte[] pdfData = PdfGeneratorForEmployeeDetail.createPdf(savedEmployee);

        // Send email with PDF attachment
        try {
            emailService.sendMimeMessage(
                    savedEmployee.getEmail(), // Employee email
                    "Welcome to Our Company",
                    "Dear " + savedEmployee.getFirstName() + ",<br><br>Please find attached your employee details.<br><br>Best regards,<br>Company Name",
                    pdfData,
                    savedEmployee.getFirstName() + "_" + savedEmployee.getLastName() + "_Details.pdf"
            );
        } catch (MessagingException e) {
            // Handle email sending failure
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Or return a suitable error response
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    @GetMapping
    @Operation(summary = "Get All Employees", description = "Ensure all fields are found")
    public ResponseEntity<List<ResponseEmpDetailDto>> getAllEmployees() {
        List<ResponseEmpDetailDto> employees = empDetailService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{employeeId}")
    @Operation(summary = "Get Employee By Id", description = "Ensure all fields are found properly")
    public ResponseEntity<ResponseEmpDetailDto> getEmployeeById(@PathVariable String employeeId) {
        ResponseEmpDetailDto employee = empDetailService.getEmployeeById(employeeId);
        return employee != null ? ResponseEntity.ok(employee) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{employeeId}")
    @Operation(summary = "Delete Employee", description = "Delete Employee By EmployeeId")
    ResponseEntity<Boolean> deleteEmployee(@PathVariable String employeeId) {
        empDetailService.deleteEmployee(employeeId);
        
        return ResponseEntity.ok(true);
    }

    @PutMapping("/{employeeId}")
    @Operation(summary = "Update Employee", description = "Update Employee By EmployeeId")
    public ResponseEntity<ResponseEmpDetailDto> updateEmployee(@PathVariable String employeeId, @ModelAttribute RequestEmpDetailDto requestEmpDetailDto) {
        // Update and get ResponseEmployeeDto
        ResponseEmpDetailDto updatedEmployee = empDetailService.updateEmployee(employeeId, requestEmpDetailDto);
        return ResponseEntity.ok(updatedEmployee);
    }

    @GetMapping("/{employeeId}/idProof")
    @Operation(summary = "Download IdProof", description = "Download IdProof of Employee By EmployeeId")
    public ResponseEntity<byte[]> downloadIdProof(@PathVariable String employeeId) {
        ResponseEmpDetailDto employeeById = empDetailService.getEmployeeById(employeeId);

        // Check if the employee exists
        if (employeeById == null || employeeById.getIdProof() == null) {
            return ResponseEntity.notFound().build();
        }

        // Get the idProof byte array
        byte[] idProof = employeeById.getIdProof();

        // Return the file as a response entity
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + employeeId + "-idProof.pdf\"") // Ensure proper file extension
                .contentType(MediaType.APPLICATION_PDF) // Use a generic binary stream type for download
                .body(idProof);
    }
    @PutMapping("/markForExit/{employeeId}")
    public ResponseEntity<ResponseEmpDetailDto> markEmployeeForExit(
            @PathVariable String employeeId,
            @RequestBody Map<String, String> requestBody) throws BadRequestException {
        String employmentStatus = requestBody.get("employmentStatus");
        if (employmentStatus == null) {
            throw new BadRequestException("EmploymentStatus is required");
        }

        EmploymentStatus status = EmploymentStatus.fromDisplayValue(employmentStatus);
        return ResponseEntity.ok(empDetailService.markEmployeesForExit(employeeId, status));
    }
}

