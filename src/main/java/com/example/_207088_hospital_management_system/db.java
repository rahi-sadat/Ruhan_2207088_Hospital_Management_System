package com.example._207088_hospital_management_system;

import java.sql.*;
import java.util.logging.Logger;

public class db {
  public Connection connection;
  private final Logger logger = Logger.getLogger(db.class.getName());
 public void getConnection() throws SQLException {
     if(connection == null || connection.isClosed()) {
         connection= DriverManager.getConnection("jdbc:sqlite:patient.db");
                 logger.info("connection established");
         createTable();
     }

  }
    public void  createTable() throws SQLException {
        getConnection();
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
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            logger.info("Patients table created or already exists.");
        }

    }


    public void insertNewPatient(String patientId, String password, String name, String phone,
                                 int age, String gender, String bloodGroup, String history) throws SQLException {

        getConnection();

        String sql = "INSERT INTO patients (patient_id, password, name, phone_number, age, gender, blood_group, medical_history) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {


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
               getConnection();
               String sql = "SELECT * FROM patients WHERE patient_id = ? AND password = ?";

               try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, patientId);
                    pstmt.setString(2, password);

                    try (ResultSet rs = pstmt.executeQuery()) {    //check
                        if (rs.next()) { return rs.getString("patient_id"); }   // paile true
                        else { return null; }
                    }


               }

    }
}

