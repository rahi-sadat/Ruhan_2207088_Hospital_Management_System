package com.example._207088_hospital_management_system;

public class Appointment {
    private int appId;
    private String patientId;
    private String doctorId;
    private String appointmentDate;
    private String status;


    private String patientName;
    private int age;
    private String gender;
    private String bloodGroup;
    private String medicalHistory;
    private String reportPath;

    public Appointment(int appId, String patientId, String doctorId, String appointmentDate, String status) {
        this.appId = appId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.status = status;
    }

    // --- SETTERS (Used by db.java) ---
    public void setPatientName(String name) { this.patientName = name; }
    public void setAge(int age) { this.age = age; }
    public void setGender(String gender) { this.gender = gender; }
    public void setBloodGroup(String bg) { this.bloodGroup = bg; }
    public void setMedicalHistory(String history) { this.medicalHistory = history; }
    public void setReportPath(String path) { this.reportPath = path; }


    public String getPatientName() { return patientName; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getBloodGroup() { return bloodGroup; }
    public String getMedicalHistory() { return medicalHistory; }
    public String getReportPath() { return reportPath; }


    public int getAppId() { return appId; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public String getAppointmentDate() { return appointmentDate; }
    public String getStatus() { return status; }
}