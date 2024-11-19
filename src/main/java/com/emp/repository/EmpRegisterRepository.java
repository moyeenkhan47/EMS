package com.emp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emp.entity.EmployeeRegister;

@Repository
public interface EmpRegisterRepository extends  JpaRepository<EmployeeRegister, String> {


	Optional<EmployeeRegister> findByEmail(String email);

}
