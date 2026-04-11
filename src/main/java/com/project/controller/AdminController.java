package com.project.controller;

import com.project.entity.Appointment;
import com.project.entity.User;
import com.project.repository.AppointmentRepository;
import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        try {
            List<User> users = userRepository.findAll();
            List<Appointment> appointments = appointmentRepository.findAll();

            long doctors = users.stream().filter(u -> "DOCTOR".equalsIgnoreCase(u.getRole())).count();
            long patients = users.stream().filter(u -> "PATIENT".equalsIgnoreCase(u.getRole())).count();
            long pharmacists = users.stream().filter(u -> "PHARMACIST".equalsIgnoreCase(u.getRole())).count();
            
            long completedAppts = appointments.stream()
                .filter(a -> "COMPLETED".equalsIgnoreCase(a.getStatus()))
                .count();

            Map<String, Object> stats = new HashMap<>();
            stats.put("totalUsers", users.size());
            stats.put("totalDoctors", doctors);
            stats.put("totalPatients", patients);
            stats.put("totalPharmacists", pharmacists);
            stats.put("totalAppointments", appointments.size());
            stats.put("completedAppointments", completedAppts);

            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
