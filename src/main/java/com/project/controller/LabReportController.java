package com.project.controller;

import com.project.entity.LabReport;
import com.project.repository.LabReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lab-reports")
public class LabReportController {

    @Autowired
    private LabReportRepository labReportRepository;

    @PostMapping
    public ResponseEntity<?> create(@RequestParam Long doctorId, @RequestBody LabReport report) {
        try {
            report.setDoctorId(doctorId);
            return ResponseEntity.ok((Object) labReportRepository.save(report));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(java.util.Map.of("error", "Database failure: " + e.getMessage()));
        }
    }

    @GetMapping
    public List<LabReport> getAll() {
        return labReportRepository.findAll();
    }

    @GetMapping("/patient/{patientId}")
    public List<LabReport> getByPatient(@PathVariable Long patientId) {
        return labReportRepository.findByPatientId(patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<LabReport> getByDoctor(@PathVariable Long doctorId) {
        return labReportRepository.findByDoctorId(doctorId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabReport> getById(@PathVariable Long id) {
        return labReportRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LabReport> update(@PathVariable Long id, @RequestBody LabReport reportDetails) {
        return labReportRepository.findById(id).map(r -> {
            r.setTestName(reportDetails.getTestName());
            r.setResult(reportDetails.getResult());
            r.setStatus(reportDetails.getStatus());
            r.setNotes(reportDetails.getNotes());
            r.setTestDate(reportDetails.getTestDate());
            return ResponseEntity.ok(labReportRepository.save(r));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<LabReport> complete(
            @PathVariable Long id, 
            @RequestParam String results, 
            @RequestParam String findings) {
        return labReportRepository.findById(id).map(r -> {
            r.setResult(results);
            r.setFindings(findings);
            r.setStatus("completed");
            return ResponseEntity.ok(labReportRepository.save(r));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return labReportRepository.findById(id).map(r -> {
            labReportRepository.delete(r);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
