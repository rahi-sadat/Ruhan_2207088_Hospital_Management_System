package com.example._207088_hospital_management_system;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class db {
    public Connection patientConn;
    public Connection doctorConn;
  private final Logger logger = Logger.getLogger(db.class.getName());
    public db() {
        try {
            getPatientConnection();
            getDoctorConnection();
            createTable();
            createDoctorTable();
            logger.info("Database initialized and tables verified.");
        } catch (SQLException e) {
            logger.severe("Could not initialize database: " + e.getMessage());
        }
    }
    public void getPatientConnection() throws SQLException {
        if (patientConn == null || patientConn.isClosed()) {
            patientConn = DriverManager.getConnection("jdbc:sqlite:patient.db");
            logger.info("Connected to patient.db");
        }
    }
    public void getDoctorConnection() throws SQLException {
        if (doctorConn == null || doctorConn.isClosed()) {
            doctorConn = DriverManager.getConnection("jdbc:sqlite:doctor.db");
            logger.info("Connected to doctor.db");
        }
    }
    public void  createTable() throws SQLException {

        String sql = "CREATE TABLE IF NOT EXISTS patients (" +
                "patient_id TEXT PRIMARY KEY," +
                "password TEXT NOT NULL," +
                "name TEXT NOT NULL," +
                "phone_number TEXT," +
                "age INTEGER," +
                "gender TEXT," +
                "blood_group TEXT," +
                "medical_history TEXT" +
                ");";
        try (Statement stmt = patientConn.createStatement()) {
            stmt.execute(sql);

        }

    }


    public void insertNewPatient(String patientId, String password, String name, String phone,
                                 int age, String gender, String bloodGroup, String history) throws SQLException {

       getPatientConnection();

        String sql = "INSERT INTO patients (patient_id, password, name, phone_number, age, gender, blood_group, medical_history) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


        try (PreparedStatement pstmt = patientConn.prepareStatement(sql)) {


            pstmt.setString(1, patientId);
            pstmt.setString(2, password);
            pstmt.setString(3, name);
            pstmt.setString(4, phone);
            pstmt.setInt(5, age);
            pstmt.setString(6, gender);
            pstmt.setString(7, bloodGroup);
            pstmt.setString(8, history);

            pstmt.executeUpdate();// execute
            logger.info("New patient registered with ID: " + patientId);
        }
    }

    public String authenticatePatient(String patientId, String password) throws SQLException {
               getPatientConnection();
               String sql = "SELECT * FROM patients WHERE patient_id = ? AND password = ?";


        try (PreparedStatement pstmt = patientConn.prepareStatement(sql)) {
                    pstmt.setString(1, patientId);
                    pstmt.setString(2, password);

                    try (ResultSet rs = pstmt.executeQuery()) {    //check
                        if (rs.next()) { return rs.getString("patient_id"); }   // paile true
                        else { return null; }
                    }


               }

    }

    public List<Patient> getAllPatients() throws SQLException {
        getPatientConnection();
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try (Statement stmt = patientConn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Patient(
                        rs.getString("patient_id"),
                        rs.getString("name"),
                        rs.getString("phone_number"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("blood_group"),
                        rs.getString("medical_history")
                ));
            }
        }
        return list;
    }


    public void createDoctorTable() {
        String sql = "CREATE TABLE IF NOT EXISTS doctors ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "specialization TEXT,"
                + "phone TEXT,"
                + "email TEXT,"
                + "password TEXT DEFAULT 'doctor',"
                + "schedule TEXT"
                + ");";
        try (Statement stmt = doctorConn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addDoctor(String name, String spec, String phone, String email, String schedule) throws SQLException {
        getDoctorConnection();
        String sql = "INSERT INTO doctors (name, specialization, phone, email, schedule) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = doctorConn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, spec);
            pstmt.setString(3, phone);
            pstmt.setString(4, email);
            pstmt.setString(5, schedule);
            pstmt.executeUpdate();
        }
    }
    public List<Doctor> getAllDoctors() throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctors";
        getDoctorConnection();


        try (Statement stmt = doctorConn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                doctors.add(new Doctor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("specialization"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("schedule")
                ));
            }
        }
        return doctors;
    }
    public String authenticateDoctor(String doctorId, String password) throws SQLException {
        getDoctorConnection();
        String sql = "SELECT * FROM doctors WHERE id = ? AND password = ?";


        try (PreparedStatement pstmt = doctorConn.prepareStatement(sql)) {
            pstmt.setString(1, doctorId);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {    //check
                if (rs.next()) { return rs.getString("id"); }   // paile true
                else { return null; }
            }


        }

    }
}

