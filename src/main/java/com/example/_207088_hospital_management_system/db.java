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
            createAppointmentTable();
            addReportColumn();
            logger.info("Database initialized and tables verified.");


        } catch (SQLException e) {
            logger.severe("Could not initialize database: " + e.getMessage());
        }
    }
    public Connection getPatientConnection() throws SQLException {

            try {

                if (patientConn == null || patientConn.isClosed()) {

                    patientConn = DriverManager.getConnection("jdbc:sqlite:patient.db");
                    logger.info("Connected to patient.db");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return patientConn;
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

    public Patient authenticatePatient(String patientId, String password) throws SQLException {
        getPatientConnection();
        String sql = "SELECT * FROM patients WHERE patient_id = ? AND password = ?";


        try (PreparedStatement pstmt = patientConn.prepareStatement(sql)) {
            pstmt.setString(1, patientId);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {    //check
                if (rs.next()) { return new Patient(
                        rs.getString("patient_id"),
                        rs.getString("name"),
                        rs.getString("phone_number"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("blood_group"),
                        rs.getString("medical_history")
                ); }   // paile true
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
    public Patient getPatientById(String id) throws SQLException {
        getPatientConnection();
        String sql = "SELECT * FROM patients WHERE patient_id = ?";

        try (PreparedStatement pstmt = patientConn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    return new Patient(
                            rs.getString("patient_id"),
                            rs.getString("name"),
                            rs.getString("phone_number"),
                            rs.getInt("age"),
                            rs.getString("gender"),
                            rs.getString("blood_group"),
                            rs.getString("medical_history")
                    );
                }
            }
        }
        return null;
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
    void createAppointmentTable() throws SQLException {
        getDoctorConnection(); // Ensure kortechi  using the doctor database
        String sql = "CREATE TABLE IF NOT EXISTS appointments (" +
                "app_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "patient_id TEXT, " +
                "doctor_id TEXT, " +
                "appointment_date TEXT, " +
                "status TEXT DEFAULT 'PENDING'" +
                ")";
        try (Statement stmt = doctorConn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public void saveAppointmentRequest(String patientId, String doctorId, String appDate) throws SQLException {
        getDoctorConnection();

        String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, status) VALUES (?, ?, ?, 'PENDING')";

        try (PreparedStatement pstmt = doctorConn.prepareStatement(sql)) {
            pstmt.setString(1, patientId);
            pstmt.setString(2, doctorId);
            pstmt.setString(3, appDate);
            pstmt.executeUpdate();
        }
    }
    public List<Appointment> getAppointmentsByStatus(String status) throws SQLException {
        getDoctorConnection();
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE status = ?";
        try (PreparedStatement pstmt = doctorConn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new Appointment(
                        rs.getInt("app_id"),
                        rs.getString("patient_id"),
                        rs.getString("doctor_id"),
                        rs.getString("appointment_date"),
                        rs.getString("status")
                ));
            }
        }
        return list;
    }

    public void updateAppointmentStatus(int appId, String newStatus) throws SQLException {
        getDoctorConnection();
        String sql = "UPDATE appointments SET status = ? WHERE app_id = ?";
        try (PreparedStatement pstmt = doctorConn.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, appId);
            pstmt.executeUpdate();
        }
    }
    public void addReportColumn() throws SQLException { // report er column add korlam
        getPatientConnection();
        String sql = "ALTER TABLE patients ADD COLUMN report_path TEXT";
        try (Statement stmt = patientConn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Column 'report_path' added successfully.");
        } catch (SQLException e) {

            System.out.println("Column might already exist: " + e.getMessage());
        }
    }
}

