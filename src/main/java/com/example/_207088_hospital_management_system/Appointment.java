package com.example._207088_hospital_management_system;

public class Appointment {
    private int appId;
    private String patientId;
    private String doctorId;
    private String appointmentDate;
    private String status;


    public Appointment(int appId, String patientId, String doctorId, String appointmentDate, String status) {
        this.appId = appId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.status = status;
    }


    public int getAppId() { return appId; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public String getAppointmentDate() { return appointmentDate; }
    public String getStatus() { return status; }
}