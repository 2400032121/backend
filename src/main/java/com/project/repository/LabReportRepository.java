package com.project.repository;

import com.project.entity.LabReport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LabReportRepository extends JpaRepository<LabReport, Long> {
    List<LabReport> findByPatientId(Long patientId);
    List<LabReport> findByDoctorId(Long doctorId);
}
