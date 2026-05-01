package com.project.service;

import com.project.entity.Prescription;
import com.project.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public Prescription create(Long doctorId, Prescription prescription) {
        prescription.setDoctorId(doctorId);
        return prescriptionRepository.save(prescription);
    }

    public List<Prescription> getAll() {
        return prescriptionRepository.findAll();
    }

    public List<Prescription> getByPatientId(Long patientId) {
        return prescriptionRepository.findByPatientId(patientId);
    }

    public List<Prescription> getByDoctorId(Long doctorId) {
        return prescriptionRepository.findByDoctorId(doctorId);
    }

    public List<Prescription> getByStatus(String status) {
        return prescriptionRepository.findByStatus(status);
    }

    public Optional<Prescription> getById(Long id) {
        return prescriptionRepository.findById(id);
    }

    public Prescription updateStatus(Long id, String status, Long pharmacistId) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found with ID: " + id));
        
        prescription.setStatus(status);
        if (pharmacistId != null) {
            prescription.setPharmacistId(pharmacistId);
        }
        return prescriptionRepository.save(prescription);
    }

    public void delete(Long id) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found with ID: " + id));
        prescriptionRepository.delete(prescription);
    }
}
