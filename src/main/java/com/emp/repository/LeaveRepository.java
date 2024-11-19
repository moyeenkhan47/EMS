package com.emp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emp.entity.Leave;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {
   
}
