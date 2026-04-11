package com.project.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "lab_reports")
public class LabReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;
    private String patientName;
    
    private Long doctorId;
    private String doctorName;
    
    private String testName;
    
    @Column(columnDefinition = "TEXT")
    private String result;
    
    @Column(columnDefinition = "TEXT")
    private String findings;
    
    private String status = "pending";
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    private String testDate;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    
    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    
    public String getTestName() { return testName; }
    public void setTestName(String testName) { this.testName = testName; }
    
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    
    public String getFindings() { return findings; }
    public void setFindings(String findings) { this.findings = findings; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public String getTestDate() { return testDate; }
    public void setTestDate(String testDate) { this.testDate = testDate; }
}
