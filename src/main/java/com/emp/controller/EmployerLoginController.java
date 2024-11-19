package com.emp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emp.security.JwtHelper;
import com.emp.security.JwtRequest;
import com.emp.service.Impl.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class EmployerLoginController {

    private final CustomUserDetailsService customService; // Ensure this service is injected
    private final JwtHelper jwtHelper;
    private final AuthenticationManager authenticationManager;
    
    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody JwtRequest request ){
    	customService.createUser(request);
		return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/authunticate")
    public ResponseEntity<Map<String, Object>> login(@RequestBody JwtRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Perform authentication
            doAuthenticate(request.getUsername(), request.getPassword());

            // Fetch user details after successful authentication
            UserDetails userDetails = customService.loadUserByUsername(request.getUsername());

            if (userDetails == null) {
                response.put("success", "false");
                response.put("message", "User not found.");
                return ResponseEntity.badRequest().body(response);
            }

            // Generate JWT token
            String token = jwtHelper.generateToken(userDetails);

            // Prepare success response
            response.put("success", "true");
            response.put("message", "Login successful!");
            response.put("jwtToken", token);

            // Return the token in the response header
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body(response);

        } catch (BadCredentialsException e) {
            // Handle invalid credentials
            response.put("success", "false");
            response.put("isLocked", false);
            response.put("message", "Invalid credentials.");
            return ResponseEntity.badRequest().body(response);
        }
    }

    private void doAuthenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Credentials Invalid!");
        }
    }
}