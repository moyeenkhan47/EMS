
package com.emp.service.Impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.emp.entity.User;
import com.emp.repository.AdminRepository;
import com.emp.security.JwtRequest;

@Service
public class CustomUserDetailsService implements UserDetailsService
{

	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = adminRepository.findByUsername(username);
	                
		  return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
		  
	}
	public void createUser(JwtRequest request) {
		 User user = new User();
	        user.setUsername(request.getUsername());
	        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        String hashedPassword = passwordEncoder.encode(request.getPassword());
	        user.setPassword(hashedPassword);  // Set the hashed password
	        
	        adminRepository.save(user);
	}

}
