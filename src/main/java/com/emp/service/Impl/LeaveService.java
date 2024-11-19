package com.emp.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.entity.Leave;
import com.emp.repository.LeaveRepository;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    public List<Leave> getAllLeaves() {
        return leaveRepository.findAll();
    }

    public Optional<Leave> getLeaveById(Long id) {
        return leaveRepository.findById(id);
    }

    public Leave createLeave(Leave leave) {
        leave.setStatus("Pending");  // Default status for new leave requests
        return leaveRepository.save(leave);
    }

    public Leave updateLeave(Long id, Leave leaveDetails) {
        return leaveRepository.findById(id).map(leave -> {
            leave.setEmployeeName(leaveDetails.getEmployeeName());
            leave.setLeaveType(leaveDetails.getLeaveType());
            leave.setStartDate(leaveDetails.getStartDate());
            leave.setEndDate(leaveDetails.getEndDate());
            leave.setStatus(leaveDetails.getStatus());
            return leaveRepository.save(leave);
        }).orElseThrow(() -> new RuntimeException("Leave not found with id " + id));
    }

    public void deleteLeave(Long id) {
        leaveRepository.deleteById(id);
    }
}
