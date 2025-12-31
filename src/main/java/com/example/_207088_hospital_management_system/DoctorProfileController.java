package com.example._207088_hospital_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorProfileController {
    @FXML private Label lblDocId, lblDocName, lblSpecialization;
    @FXML private PasswordField txtNewPassword, txtConfirmPassword;

    @FXML
    public void initialize() {
        String currentId = UserSession.getUserId();
        lblDocId.setText(currentId);

        try {
            db database = new db();
            Doctor doc = database.getDoctorDetails(currentId);

            if (doc != null) {
                lblDocName.setText(doc.getName());
                lblSpecialization.setText(doc.getSpecialization());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            lblDocName.setText("Error loading data");
        }
    }

@FXML
private void handleUpdatePassword(ActionEvent event) {
    String newPass = txtNewPassword.getText().trim();
    String confirmPass = txtConfirmPassword.getText().trim();
    String currentId = UserSession.getUserId();

    // Debugging: Print to console to see what is missing
    System.out.println("Attempting update for ID: " + currentId);

    if (currentId == null) {
        new Alert(Alert.AlertType.ERROR, "Session expired. Please log in again.").show();
        return;
    }

    if (newPass.isEmpty() || !newPass.equals(confirmPass)) {
        new Alert(Alert.AlertType.WARNING, "Passwords must match and not be empty!").show();
        return;
    }

    try {
        db database = new db();
        database.updateDoctorPassword(currentId, newPass);

        new Alert(Alert.AlertType.INFORMATION, "Password updated successfully!").show();
        txtNewPassword.clear();
        txtConfirmPassword.clear();

    } catch (SQLException e) {
        e.printStackTrace();
        new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
    }
}
}
