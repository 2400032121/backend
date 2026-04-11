package com.project.controller;

import com.project.entity.Prescription;
import com.project.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @PostMapping
    public Prescription create(@RequestParam Long doctorId, @RequestBody Prescription prescription) {
        prescription.setDoctorId(doctorId);
        return prescriptionRepository.save(prescription);
    }

    @GetMapping
    public List<Prescription> getAll() {
        return prescriptionRepository.findAll();
    }

    @GetMapping("/patient/{patientId}")
    public List<Prescription> getByPatient(@PathVariable Long patientId) {
        return prescriptionRepository.findByPatientId(patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<Prescription> getByDoctor(@PathVariable Long doctorId) {
        return prescriptionRepository.findByDoctorId(doctorId);
    }

    @GetMapping("/status/{status}")
    public List<Prescription> getByStatus(@PathVariable String status) {
        return prescriptionRepository.findByStatus(status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getById(@PathVariable Long id) {
        return prescriptionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id, 
            @RequestParam String status, 
            @RequestParam(required = false) Long pharmacistId) {
        try {
            return prescriptionRepository.findById(id).map(p -> {
                p.setStatus(status);
                if (pharmacistId != null) {
                    p.setPharmacistId(pharmacistId);
                }
                return ResponseEntity.ok((Object) prescriptionRepository.save(p));
            }).orElse(ResponseEntity.status(404).body(java.util.Map.of("error", "Prescription not found with ID: " + id)));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(java.util.Map.of("error", "Database transaction failed: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return prescriptionRepository.findById(id).map(p -> {
            prescriptionRepository.delete(p);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
