package com.example._207088_hospital_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class PatientRegistrationController {
    @FXML private TextField txtPatientName;
    @FXML private TextField txtPhoneNumber;
    @FXML private TextField txtAge;
    @FXML private ComboBox<String> cbGender;
    @FXML private ComboBox<String> cbBloodGroup;
    @FXML private TextArea txtMedicalHistory;
    @FXML private PasswordField txtPassword;

      private final Logger logger = Logger.getLogger(PatientRegistrationController.class.getName());
    public void initialize() {
        // combox e option dilam
        cbGender.getItems().addAll("Male", "Female", "Other");
        cbBloodGroup.getItems().addAll("A+", "A-", "B+", "B-","AB+","AB-", "O+", "O-");
    }


    private String generatePatientId( db database) throws SQLException {
        if (database == null) return "PAT-ERROR";

        database.getConnection(); // Ensure connection is open

        // SQL to find the highest number in the patient_id column
        String sql = "SELECT COUNT(*) FROM patients";

        int rowCount = 0;

        try (Statement stmt = database.connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                rowCount = rs.getInt(1);
            }
        }
        int newSerial = rowCount + 1;

        // Format the new serial number (e.g., PAT00001)
        return "PAT" + String.format("%05d", newSerial);
    }


    public void handleRegister(ActionEvent actionEvent) {
        String name = txtPatientName.getText();
        String password = txtPassword.getText();
        String ageText = txtAge.getText();

        if (name.isEmpty() || password.isEmpty() || ageText.isEmpty() || cbGender.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Please fill in Name, Password, Age, and Gender.").showAndWait();
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid age format. Please enter a number.").showAndWait();
            return;
        }
        db database = new db();
        try {
           // id generate er jonno pathalam
            String patientId = generatePatientId(database);


            String phone = txtPhoneNumber.getText();
            String gender = cbGender.getValue();
            String bloodGroup = cbBloodGroup.getValue();
            String history = txtMedicalHistory.getText();

           // data insert kortechi , ar function ta db.java te ache
            database.insertNewPatient(patientId, password, name, phone, age, gender, bloodGroup, history);

                    // hoiche thik moto
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registration Successful");
            alert.setHeaderText("Account Created!");
            alert.setContentText("Your unique Patient ID is: " + patientId + "\nPlease use this ID and your password to log in.");
            alert.showAndWait();

            logger.info("Successfully registered new patient: " + patientId);
            closeWindow(actionEvent);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error during registration: " + e.getMessage()).showAndWait();
            logger.severe("Database Error: " + e.getMessage());
        }
    }


    public void handleCloseWindow(ActionEvent actionEvent) {
        closeWindow(actionEvent);
    }
    private void closeWindow(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
