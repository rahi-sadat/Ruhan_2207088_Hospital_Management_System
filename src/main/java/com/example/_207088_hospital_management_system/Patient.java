package com.example._207088_hospital_management_system;



public class Patient {

    private String patientId;
    private  String name;
    private String  phone;
    private Integer age;
    private String  gender;
    private String  bloodGroup;
    private String  history;

    public Patient(String patientId, String name, String phone, int age, String gender, String bloodGroup, String history) {
        this.patientId = patientId;
        this.name = name;
        this.phone = phone;
        this.age = age;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.history =history;
    }


    public String getPatientId() { return patientId ;}
    public String getName() { return name ;}
    public String getPhone() { return phone ;}
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getBloodGroup() { return bloodGroup; }
    public String getHistory() { return history; }


}