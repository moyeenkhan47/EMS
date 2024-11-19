package com.emp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emp.entity.EmployeeDetail;
@Repository
public interface EmpDetailRepository extends JpaRepository<EmployeeDetail, String> {

	
}
