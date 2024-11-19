package com.emp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emp.entity.ExitEmployee;

@Repository
public interface ExitEmployeeRepository extends JpaRepository<ExitEmployee, Long> {

    // Find active employees by IDs
    List<ExitEmployee> findByEmployeeIdInAndIsExitedFalse(List<Long> employeeIds);

    // Find all exited employees
    List<ExitEmployee> findByIsExitedTrue();
}