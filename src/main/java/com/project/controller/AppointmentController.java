package com.project.controller;

import com.project.entity.Appointment;
import com.project.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @PostMapping("/{patientId}")
    public Appointment create(@PathVariable Long patientId, @RequestBody Appointment appointment) {
        appointment.setPatientId(patientId);
        return appointmentRepository.save(appointment);
    }

    @GetMapping
    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    @GetMapping("/patient/{patientId}")
    public List<Appointment> getByPatient(@PathVariable Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getByDoctor(@PathVariable Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getById(@PathVariable Long id) {
        return appointmentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Appointment> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return appointmentRepository.findById(id).map(a -> {
            a.setStatus(status);
            return ResponseEntity.ok(appointmentRepository.save(a));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/notes")
    public ResponseEntity<Appointment> updateNotes(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return appointmentRepository.findById(id).map(a -> {
            a.setNotes(body.get("notes"));
            return ResponseEntity.ok(appointmentRepository.save(a));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return appointmentRepository.findById(id).map(a -> {
            appointmentRepository.delete(a);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
