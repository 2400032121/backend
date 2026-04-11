package com.project.controller;

import com.project.entity.MedicalRecord;
import com.project.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @PostMapping
    public ResponseEntity<?> create(@RequestParam Long doctorId, @RequestParam Long patientId, @RequestBody MedicalRecord record) {
        try {
            record.setDoctorId(doctorId);
            record.setPatientId(patientId);
            return ResponseEntity.ok((Object) medicalRecordRepository.save(record));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(java.util.Map.of("error", "Database validation failed: " + e.getMessage()));
        }
    }

    @GetMapping("/patient/{patientId}")
    public List<MedicalRecord> getByPatient(@PathVariable Long patientId) {
        return medicalRecordRepository.findByPatientId(patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<MedicalRecord> getByDoctor(@PathVariable Long doctorId) {
        return medicalRecordRepository.findByDoctorId(doctorId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecord> getById(@PathVariable Long id) {
        return medicalRecordRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecord> update(@PathVariable Long id, @RequestBody MedicalRecord recordDetails) {
        return medicalRecordRepository.findById(id).map(r -> {
            r.setDiagnosis(recordDetails.getDiagnosis());
            r.setSymptoms(recordDetails.getSymptoms());
            r.setTreatment(recordDetails.getTreatment());
            r.setNotes(recordDetails.getNotes());
            return ResponseEntity.ok(medicalRecordRepository.save(r));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return medicalRecordRepository.findById(id).map(r -> {
            medicalRecordRepository.delete(r);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
