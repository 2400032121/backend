package com.project.controller;

import com.project.entity.Prescription;
import com.project.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping
    public Prescription create(@RequestParam Long doctorId, @RequestBody Prescription prescription) {
        return prescriptionService.create(doctorId, prescription);
    }

    @GetMapping
    public List<Prescription> getAll() {
        return prescriptionService.getAll();
    }

    @GetMapping("/patient/{patientId}")
    public List<Prescription> getByPatient(@PathVariable Long patientId) {
        return prescriptionService.getByPatientId(patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<Prescription> getByDoctor(@PathVariable Long doctorId) {
        return prescriptionService.getByDoctorId(doctorId);
    }

    @GetMapping("/status/{status}")
    public List<Prescription> getByStatus(@PathVariable String status) {
        return prescriptionService.getByStatus(status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getById(@PathVariable Long id) {
        return prescriptionService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id, 
            @RequestParam String status, 
            @RequestParam(required = false) Long pharmacistId) {
        try {
            Prescription updated = prescriptionService.updateStatus(id, status, pharmacistId);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(404).body(java.util.Map.of("error", e.getMessage()));
            }
            return ResponseEntity.status(500).body(java.util.Map.of("error", "Database transaction failed: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            prescriptionService.delete(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
