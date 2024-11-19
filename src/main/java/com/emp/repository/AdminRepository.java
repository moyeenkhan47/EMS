package com.emp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emp.entity.User;
@Repository
public interface AdminRepository extends JpaRepository<User, String>{

	User findByUsername(String username);

}
